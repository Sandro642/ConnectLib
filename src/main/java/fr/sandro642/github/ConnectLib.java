package fr.sandro642.github;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.sandro642.github.enums.LangType;
import fr.sandro642.github.enums.lang.CategoriesType;
import fr.sandro642.github.hook.HookManager;
import fr.sandro642.github.hook.LangSupport;
import fr.sandro642.github.hook.MCSupport;
import fr.sandro642.github.log.Logger;
import fr.sandro642.github.log.Logs;
import fr.sandro642.github.misc.*;
import fr.sandro642.github.jobs.JobGetInfos;
import fr.sandro642.github.enums.ResourceType;
import fr.sandro642.github.spring.Application;
import fr.sandro642.github.update.RetrieveLastVersion;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

/**
 * ConnectLib is the main class of the ConnectorAPI library for Standard API.
 * It provides methods to initialize the library with resource types, routes, and schemas,
 * @author Sandro642
 * @version 1.0
 * @see ConnectLib#JobGetInfos()
 * @see ConnectLib#Logger()
 * @see ConnectLib#StoreAndRetrieve()
 * @see ConnectLib#YamlUtils()
 * @see ConnectLib#MCSupport()
 * @see ConnectLib#HookManager()
 * @see ConnectLib#LangSupport()
 * @see ConnectLib#LangManager()
 */

public class ConnectLib {

    private static Logger logger = new Logger();
    private static StoreAndRetrieve storeAndRetrieve = new StoreAndRetrieve();
    private static YamlUtils yamlUtils = new YamlUtils();
    private static final Map<String,String> routes = new HashMap<>();
    private static Logs logs = new Logs();
    private static LangManager langManager;
    private static RetrieveLastVersion rlv;

    private static boolean isInitialized = false;

    private static List<String> enumNames = new ArrayList<>();
    private static List<String> enumUrls = new ArrayList<>();
    private static Map<Enum<?>, String> routesEnums = new HashMap<>();

    /**
     * Init the ConnectLib with the specified resource type and routes.
     * @param resourceType the type of resource to initialize
     * @param routes the routes to be used in the ConnectLib
     */
    public ConnectLib init(ResourceType resourceType, LangType langType, Class<? extends Enum<?>>... routes) {
        try {
            logger = new Logger();
            storeAndRetrieve = new StoreAndRetrieve();
            yamlUtils = new YamlUtils();
            logs = new Logs();
            rlv = new RetrieveLastVersion();
            isInitialized = true;

            rlv.isLatestVersion();

            HookManager().initHook(resourceType);
            LangSupport().setLangTypeVariable(langType);
            HookManager().FILE_LOCATION_KEY();

            langManager = new LangManager();

            for (Class<? extends Enum<?>> route : routes) {
                if (route == null) {
                    Logger().ERROR(langManager.getMessage(CategoriesType.CONNECTLIB_CLASS, "initialise.routeclass"));
                    continue;
                }

                Map<Enum<?>, String> converted = EnumLoader.convertRouteImport(route);
                if (converted != null && !converted.isEmpty()) {
                    routesEnums.putAll(converted);
                    for (Map.Entry<Enum<?>, String> entry : converted.entrySet()) {
                        enumNames.add(entry.getKey().name());
                        enumUrls.add(entry.getValue());
                    }
                }
            }


            Map<Enum<?>, String> routesEnums = new HashMap<>();
            for (Class<? extends Enum<?>> route : routes) {
                if (route == null) {
                    Logger().ERROR(langManager.getMessage(CategoriesType.CONNECTLIB_CLASS, "initialise.routeclass"));
                    continue;
                }
                routesEnums.putAll(EnumLoader.convertRouteImport(route));
            }

            yamlUtils.generateTemplateIfNotExists(routesEnums);
            logs.setPathFile();

            String baseUrl = yamlUtils.getURL();
            if (baseUrl != null) {
                storeAndRetrieve.store.put(storeAndRetrieve.URL_KEY, baseUrl);
            }

            Map<String, String> yamlRoutes = yamlUtils.getRoutes();
            if (yamlRoutes != null) {
                ConnectLib.routes.putAll(yamlRoutes);
            }
        } catch (Exception e) {
            Logger().ERROR(langManager.getMessage(CategoriesType.CONNECTLIB_CLASS, "initialise.catcherror", Map.of("exception", e.getMessage())));
        }
        return this;
    }

    /**
     * Get the routes map.
     * @return a map of route names to their corresponding paths
     */
    public Map<String, String> getRoutesMap() {
        return new HashMap<>(routes);
    }

    /**
     * Check if the ConnectLib supports web services.
     * This method starts the Spring application.
     */
    public void webServices() {
        storeAndRetrieve.put(storeAndRetrieve.PORT, 3000);
        SpringApp().startApplication().subscribe();
    }

    /**
     * Check if the ConnectLib supports web services on a specific port with a dashboard name.
     * This method starts the Spring application.
     * @param port the port number to run the web services on
     *             If port is 0, the default port 3000 will be used
     * @param nameDashboard the name of the dashboard
     */
    public void webServices(int port, String nameDashboard) {
        storeAndRetrieve.put(storeAndRetrieve.NAME_DASHBOARD, nameDashboard);

        if (port == 0) {
            storeAndRetrieve.put(storeAndRetrieve.PORT, 3000);
        } else {
            storeAndRetrieve.put(storeAndRetrieve.PORT, port);
        }


        SpringApp().startApplication().subscribe();
    }

    /**
     * Implement WAN connection to retrieve the port from the server.
     * @param urlServ the server URL
     */
    public void wanImplement(String urlServ) {
        try {
            String codeWan = "";

            HttpClient client1 = HttpClient.newHttpClient();
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create(urlServ)) // remplacer par l'URL qui
                    .build();

            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());

            Gson gson1 = new Gson();
            JsonObject root1 = gson1.fromJson(response1.body(), JsonObject.class);

            if (root1 != null) {
                codeWan = root1.get("code").getAsString();
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlServ + "/connect/" + codeWan)) // remplacer par l'URL qui renvoie le JSON
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject root = gson.fromJson(response.body(), JsonObject.class);

            if (root != null && root.has("user")) {
                JsonObject user = root.getAsJsonObject("user");
                if (user != null && user.has("port") && !user.get("port").isJsonNull()) {
                    StoreAndRetrieve().put(StoreAndRetrieve().DYNAMIC_PORT, user.get("port").getAsString());
                }
            }

            SpringApp().startApplication().subscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if the ConnectLib is initialized.
     * @return true if initialized, false otherwise
     */
    public boolean isInitialized() {
        return isInitialized;
    }

    /**
     * Return the route associated with the given route name.
     * @param routeName the name of the route to retrieve
     * @return the route as a String
     */
    public String getRoute(String routeName) {
        if (routes.containsKey(routeName)) {
            return routes.get(routeName);
        } else {
            Logger().ERROR(langManager.getMessage(CategoriesType.CONNECTLIB_CLASS, "getroute.error", Map.of("route", routeName)));
            return null;
        }
    }

    /**
     * Return an instance of JobGetInfos.
     * @return JobGetInfos instance
     */
    public JobGetInfos JobGetInfos() {
        return new JobGetInfos();
    }

    /**
     * Return the instance of Logger.
     * @return Logger instance
     */
    public Logger Logger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    /**
     * Return the instance of StoreAndRetrieve.
     * @return StoreAndRetrieve instance
     */
    public StoreAndRetrieve StoreAndRetrieve() {
        if (storeAndRetrieve == null) {
            storeAndRetrieve = new StoreAndRetrieve();
        }
        return storeAndRetrieve;
    }

    /**
     * Return the instance of YamlUtils.
     * @return YamlUtils instance
     */
    public YamlUtils YamlUtils() {
        if (yamlUtils == null) {
            yamlUtils = new YamlUtils();
        }
        return yamlUtils;
    }

    /**
     * Return the instance of Logs.
     * @return Logs instance
     */
    public MCSupport MCSupport() {
        return MCSupport.getInstance();
    }

    /**
     * Return the instance of HookManager.
     * @return HookManager instance
     */
    public HookManager HookManager() {
        return HookManager.getInstance();
    }

    /**
     * Return the instance of LangSupport.
     * @return LangSupport instance²
     */
    public LangSupport LangSupport() {
        return LangSupport.getInstance();

    }

    /**
     * Return the instance of LangManager.
     * @return LangManager instance
     */
    public LangManager LangManager() {
        return langManager;
    }

    /**
     * Return the instance of Application.
     * @return Application instance
     */
    public Application SpringApp() {
        return Application.getInstance();
    }
}
