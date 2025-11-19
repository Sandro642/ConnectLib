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

import java.io.IOException;
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
     * Implement WAN connectivity using the provided server URL and component name.
     * This method performs HTTP requests to establish a connection and retrieve necessary information.
     * @param urlServ the URL of the server to connect to
     * @param nameComponent the name of the component to be used in the connection
     */
    public void wanImplement(String urlServ, String nameComponent) {
        if (urlServ == null || urlServ.isBlank()) {
            return;
        }
        try {
            StoreAndRetrieve().put(StoreAndRetrieve().NAME_DASHBOARD, nameComponent);

            String trimmed = urlServ.trim();
            if (!trimmed.matches("(?i)^https?://.*")) trimmed = "http://" + trimmed;
            if (trimmed.endsWith("/")) trimmed = trimmed.substring(0, trimmed.length() - 1);
            String urlServLambda = trimmed + "/api";

            URI uri = URI.create(urlServLambda);

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(java.time.Duration.ofSeconds(5))
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();

            HttpRequest req1 = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .timeout(java.time.Duration.ofSeconds(5))
                    .header("Accept", "application/json")
                    .header("User-Agent", "ConnectLib/1.0")
                    .build();

            try {
                HttpResponse<String> resp1 = client.send(req1, HttpResponse.BodyHandlers.ofString());

                if (resp1.statusCode() / 100 != 2 || resp1.body() == null || resp1.body().isBlank()) {
                    return;
                }

                JsonObject root1 = new Gson().fromJson(resp1.body(), JsonObject.class);
                if (root1 == null || !root1.has("code") || root1.get("code").isJsonNull()) {
                    return;
                }
                String codeWan = root1.get("code").getAsString();

                String connectUrl = trimmed + "/api/connect/" + java.net.URLEncoder.encode(codeWan, java.nio.charset.StandardCharsets.UTF_8);
                HttpRequest req2 = HttpRequest.newBuilder()
                        .uri(URI.create(connectUrl))
                        .GET()
                        .timeout(java.time.Duration.ofSeconds(5))
                        .header("Accept", "application/json")
                        .header("User-Agent", "ConnectLib/1.0")
                        .build();

                HttpResponse<String> resp2 = client.send(req2, HttpResponse.BodyHandlers.ofString());

                if (resp2.statusCode() / 100 == 2 && resp2.body() != null && !resp2.body().isBlank()) {
                    JsonObject root = new Gson().fromJson(resp2.body(), JsonObject.class);
                    if (root != null && root.has("user")) {
                        JsonObject user = root.getAsJsonObject("user");
                        if (user != null && user.has("port") && !user.get("port").isJsonNull()) {
                            StoreAndRetrieve().put(StoreAndRetrieve().DYNAMIC_PORT, user.get("port").getAsString());
                        }
                    }
                }

                SpringApp().startApplication().subscribe();
                return;
            } catch (java.io.EOFException eof) {
                Logger().ERROR(langManager.getMessage(CategoriesType.CONNECTLIB_CLASS, "wanimplement.erroreefexception", Map.of("exception", eof.getMessage())));
            } catch (IOException ioe) {
                Logger().ERROR(langManager.getMessage(CategoriesType.CONNECTLIB_CLASS, "wanimplement.errorioexception", Map.of("exception", ioe.getMessage())));
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                return;
            }

            try {
                java.net.URL url = new java.net.URL(urlServLambda);
                java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("User-Agent", "ConnectLib/1.0");
                conn.setRequestProperty("Connection", "close");
                int code = conn.getResponseCode();
                String body;
                try (java.io.InputStream is = code >= 400 ? conn.getErrorStream() : conn.getInputStream()) {
                    body = is == null ? "<no-body>" : new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
                }
            } catch (Exception e) {
                Logger().ERROR(langManager.getMessage(CategoriesType.CONNECTLIB_CLASS, "wanimplement.errorfallback", Map.of("exception", e.getMessage())));
            }

        } catch (IllegalArgumentException iae) {
            Logger().ERROR(langManager.getMessage(CategoriesType.CONNECTLIB_CLASS, "wanimplement.illegalargument", Map.of("exception", iae.getMessage())));
        } catch (Exception e) {
            Logger().ERROR(langManager.getMessage(CategoriesType.CONNECTLIB_CLASS, "wanimplement.error", Map.of("exception", e.getMessage())));
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
