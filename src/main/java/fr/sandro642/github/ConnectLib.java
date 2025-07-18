package fr.sandro642.github;

import fr.sandro642.github.hook.MCSupport;
import fr.sandro642.github.utils.*;
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
 */

public class ConnectLib {

    private static Logger logger = new Logger();
    private static StoreAndRetrieve storeAndRetrieve = new StoreAndRetrieve();
    private static YamlUtils yamlUtils = new YamlUtils();
    private static final Map<String,String> routes = new HashMap<>();
    private static Logs logs = new Logs();

    /**
     * Init the ConnectLib with the specified resource type and routes.
     * @param resourceType the type of resource to initialize
     * @param routes the routes to be used in the ConnectLib
     */
    public static void initialize(ResourceType resourceType, Class<? extends Enum<?>>... routes) {
        try {
            Map<Enum<?>, String> routesEnums = new HashMap<>();
            for (Class<? extends Enum<?>> route : routes) {
                if (route == null) {
                    ConnectLib.Logger().ERROR("Route class cannot be null.");
                }

                routesEnums.putAll(ConvertEnum.convertRouteImport(route));
            }

            logger = new Logger();
            storeAndRetrieve = new StoreAndRetrieve();
            yamlUtils = new YamlUtils();
            logs = new Logs();

            yamlUtils.generateTemplateIfNotExists(resourceType, routesEnums);
            logs.setPathFile(resourceType);

            if (resourceType == ResourceType.MC_RESOURCES) {
                storeAndRetrieve.store.put(storeAndRetrieve.FILE_LOCATION_KEY, MCSupport().getPluginPath());
            } else {
                storeAndRetrieve.store.put(storeAndRetrieve.FILE_LOCATION_KEY, resourceType.getPath());
            }

            String baseUrl = yamlUtils.getURL();
            if (baseUrl != null) {
                storeAndRetrieve.store.put(storeAndRetrieve.URL_KEY, baseUrl);
            }

            Map<String, String> yamlRoutes = yamlUtils.getRoutes();
            if (yamlRoutes != null) {
                ConnectLib.routes.putAll(yamlRoutes);
            }
        } catch (Exception e) {
            ConnectLib.Logger().ERROR("Error while initializing ConnectLib: " + e.getMessage());
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
            ConnectLib.Logger().ERROR("Route '" + routeName + "' not found in the ConnectorAPI routes.");
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
}
