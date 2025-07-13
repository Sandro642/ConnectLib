package fr.sandro642.github;

import fr.sandro642.github.hook.MCSupport;
import fr.sandro642.github.utils.ConvertEnum;
import fr.sandro642.github.jobs.JobGetInfos;
import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.utils.Logger;
import fr.sandro642.github.utils.StoreAndRetrieve;
import fr.sandro642.github.utils.YamlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ConnectorAPI est une librairie Java permettant de créer un lien de communication entre son projet Java et une API Rest Fastify.
 * @author Sandro642
 * @version 1.0
 * @see ConnectorAPI#JobGetInfos()
 * @see ConnectorAPI#Logger()
 * @see ConnectorAPI#StoreAndRetrieve()
 * @see ConnectorAPI#YamlUtils()
 * @see ConnectorAPI#MCSupport()
 */

public class ConnectorAPI {

    private static Logger logger = new Logger();
    private static StoreAndRetrieve storeAndRetrieve = new StoreAndRetrieve();
    private static YamlUtils yamlUtils = new YamlUtils();
    private static final Map<String,String> routes = new HashMap<>();
    private static final Map<String, String> schemas = new HashMap<>();

    /**
     * Initialise le ConnectorAPI avec le type de ressource, les routes et les schémas
     * @param resourceType Le type de ressource à utiliser
     * @param routes Les classes d'énumération contenant les routes
     */
    public static void initialize(ResourceType resourceType, Class<? extends Enum<?>>... routes) {

        Map<Enum<?>,String> routesEnums = new HashMap<>();
        for (Class<? extends Enum<?>> route : routes) {
            if (route == null) {
                throw new IllegalArgumentException("Les routes ne peuvent pas être null");
            }

            routesEnums.putAll(ConvertEnum.convertRouteImport(route));
        }

        logger = new Logger();
        storeAndRetrieve = new StoreAndRetrieve();
        yamlUtils = new YamlUtils();

        // Génère le template si nécessaire
        yamlUtils.generateTemplateIfNotExists(resourceType, routesEnums);

        if (resourceType == ResourceType.MC_RESOURCES) {
            storeAndRetrieve.store.put(storeAndRetrieve.FILE_LOCATION_KEY, MCSupport().getPluginPath());
        } else {
            storeAndRetrieve.store.put(storeAndRetrieve.FILE_LOCATION_KEY, resourceType.getPath());
        }

        // Charge l'URL depuis le fichier YAML
        String baseUrl = yamlUtils.getURL();
        if (baseUrl != null) {
            storeAndRetrieve.store.put(storeAndRetrieve.URL_KEY, baseUrl);
        }

        // FIX: Vérifier que getRoutes() ne retourne pas null avant putAll
        Map<String, String> yamlRoutes = yamlUtils.getRoutes();
        if (yamlRoutes != null) {
            ConnectorAPI.routes.putAll(yamlRoutes);
        }
    }

    /**
     * Retourne une map contenant les routes définies dans le ConnectorAPI
     * @return une map de routes
     */
    public static String getRoute(String routeName) {
        if (routes.containsKey(routeName)) {
            return routes.get(routeName);
        } else {
            throw new IllegalArgumentException("La route " + routeName + " n'existe pas.");
        }
    }

    /**
     * Retourne une map contenant les schémas définis dans le ConnectorAPI
     * @return une map de schémas
     */
    public static String getRoute(Enum<?> routeEnum) {
        return getRoute(routeEnum.name().toLowerCase());
    }

    /**
     * Retourne une instance de JobGetInfos pour les opérations API
     */
    public static JobGetInfos JobGetInfos() {
        return new JobGetInfos();
    }

    /**
     * Retourne l'instance du Logger
     */
    public static Logger Logger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    /**
     * Retourne l'instance de StoreAndRetrieve
     */
    public static StoreAndRetrieve StoreAndRetrieve() {
        if (storeAndRetrieve == null) {
            storeAndRetrieve = new StoreAndRetrieve();
        }
        return storeAndRetrieve;
    }

    /**
     * Retourne l'instance de YamlUtils
     */
    public static YamlUtils YamlUtils() {
        if (yamlUtils == null) {
            yamlUtils = new YamlUtils();
        }
        return yamlUtils;
    }

    /**
     * Retourne une instance de MCSupport si le projet est un projet Minecraft
     * @return MCSupport ou null si ce n'est pas un projet Minecraft
     */
    public static MCSupport MCSupport() {
        return MCSupport.getInstance();
    }
}
