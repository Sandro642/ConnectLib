package fr.sandro642.github;

import fr.sandro642.github.enums.LangType;
import fr.sandro642.github.hook.HookManager;
import fr.sandro642.github.hook.LangSupport;
import fr.sandro642.github.hook.MCSupport;
import fr.sandro642.github.misc.*;
import fr.sandro642.github.jobs.JobGetInfos;
import fr.sandro642.github.enums.ResourceType;

import java.util.HashMap;
import java.util.Map;

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
 */

public class ConnectLib {

    private static Logger logger = new Logger();
    private static StoreAndRetrieve storeAndRetrieve = new StoreAndRetrieve();
    private static YamlUtils yamlUtils = new YamlUtils();
    private static final Map<String,String> routes = new HashMap<>();
    private static Logs logs = new Logs();
    private static LangManager langManager;

    /**
     * Init the ConnectLib with the specified resource type and routes.
     * @param resourceType the type of resource to initialize
     * @param routes the routes to be used in the ConnectLib
     */
    public static void initialize(ResourceType resourceType, LangType langType, Class<? extends Enum<?>>... routes) {
        try {
            logger = new Logger();
            storeAndRetrieve = new StoreAndRetrieve();
            yamlUtils = new YamlUtils();
            logs = new Logs();

            HookManager().initHook(resourceType);
            LangSupport().setLangTypeVariable(langType);
            HookManager().FILE_LOCATION_KEY();

            langManager = new LangManager();

            Map<Enum<?>, String> routesEnums = new HashMap<>();
            for (Class<? extends Enum<?>> route : routes) {
                if (route == null) {
                    ConnectLib.Logger().ERROR(langManager.getMessage("connectlib.class", "initialise.routeclass"));
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
            ConnectLib.Logger().ERROR(langManager.getMessage("connectlib.class", "initialise.catcherror", Map.of("exception", e.getMessage())));
        }
    }

    /**
     * Return the route associated with the given route name.
     * @param routeName the name of the route to retrieve
     * @return the route as a String
     */
    public static String getRoute(String routeName) {
        if (routes.containsKey(routeName)) {
            return routes.get(routeName);
        } else {
            ConnectLib.Logger().ERROR(langManager.getMessage("connectlib.class", "getroute.error", Map.of("route", routeName)));
            return null;
        }
    }

    /**
     * Return the route associated with the given Enum route.
     * The Enum name is converted to lowercase to match the route names.
     * @param routeEnum the Enum representing the route
     * @return the route as a String
     */
    public static String getRoute(Enum<?> routeEnum) {
        return getRoute(routeEnum.name().toLowerCase());
    }

    /**
     * Return an instance of JobGetInfos.
     * @return JobGetInfos instance
     */
    public static JobGetInfos JobGetInfos() {
        return new JobGetInfos();
    }

    /**
     * Return the instance of Logger.
     * @return Logger instance
     */
    public static Logger Logger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    /**
     * Return the instance of StoreAndRetrieve.
     * @return StoreAndRetrieve instance
     */
    public static StoreAndRetrieve StoreAndRetrieve() {
        if (storeAndRetrieve == null) {
            storeAndRetrieve = new StoreAndRetrieve();
        }
        return storeAndRetrieve;
    }

    /**
     * Return the instance of YamlUtils.
     * @return YamlUtils instance
     */
    public static YamlUtils YamlUtils() {
        if (yamlUtils == null) {
            yamlUtils = new YamlUtils();
        }
        return yamlUtils;
    }

    /**
     * Return the instance of Logs.
     * @return Logs instance
     */
    public static MCSupport MCSupport() {
        return MCSupport.getInstance();
    }

    /**
     * Return the instance of HookManager.
     * @return HookManager instance
     */
    public static HookManager HookManager() {
        return HookManager.getInstance();
    }

    /**
     * Return the instance of LangSupport.
     * @return LangSupport instance
     */
    public static LangSupport LangSupport() {
        return LangSupport.getInstance();

    }

    /**
     * Return the instance of LangManager.
     * @return LangManager instance
     */
    public static LangManager LangManager() {
        return langManager;
    }
}
