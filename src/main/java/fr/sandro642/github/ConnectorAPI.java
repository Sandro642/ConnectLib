package fr.sandro642.github;

import fr.sandro642.github.jobs.JobGetInfos;
import fr.sandro642.github.jobs.misc.MethodType;
import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.jobs.misc.VersionType;
import fr.sandro642.github.utils.Logger;
import fr.sandro642.github.utils.StoreAndRetrieve;
import fr.sandro642.github.utils.YamlUtils;

/**
 * ConnectorAPI est une librairie Java permettant de créer un lien de communication entre son projet Java et une API Rest Fastify.
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 * @see ConnectorAPI#initialize(ResourceType)
 * @see ConnectorAPI#JobGetInfos()
 * @see ConnectorAPI#Logger()
 * @see ConnectorAPI#StoreAndRetrieve()
 * @see ConnectorAPI#YamlUtils()
 */

public class ConnectorAPI {

    private Logger logger = new Logger();
    private StoreAndRetrieve storeAndRetrieve = new StoreAndRetrieve();
    private YamlUtils yamlUtils = new YamlUtils();

    /**
     * Création de l'instance de ConnectorAPI.
     */
    private static ConnectorAPI instance;

    /**
     * Initialise le ConnectorAPI avec le type de ressource spécifié
     */
    public void initialize(ResourceType resourceType) {
        logger = new Logger();
        storeAndRetrieve = new StoreAndRetrieve();
        yamlUtils = new YamlUtils();

        // Génère le template si nécessaire
        yamlUtils.generateTemplateIfNotExists(resourceType);

        storeAndRetrieve.store.put(storeAndRetrieve.FILE_LOCATION_KEY, resourceType.getPath());

        // Charge l'URL depuis le fichier YAML
        String baseUrl = yamlUtils.getURL();
        if (baseUrl != null) {
            storeAndRetrieve.store.put(storeAndRetrieve.URL_KEY, baseUrl);
        }
    }

    /**
     * Retourne une instance de JobGetInfos pour les opérations API
     */
    public JobGetInfos JobGetInfos() {
        return new JobGetInfos();
    }

    /**
     * Retourne l'instance du Logger
     */
    public Logger Logger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    /**
     * Retourne l'instance de StoreAndRetrieve
     */
    public StoreAndRetrieve StoreAndRetrieve() {
        if (storeAndRetrieve == null) {
            storeAndRetrieve = new StoreAndRetrieve();
        }
        return storeAndRetrieve;
    }

    /**
     * Retourne l'instance de YamlUtils
     */
    public YamlUtils YamlUtils() {
        if (yamlUtils == null) {
            yamlUtils = new YamlUtils();
        }
        return yamlUtils;
    }

    /**
     * Retourne les énumérations disponibles pour les types de ressources.
     */
    public ResourceType[] getResourceTypes() {
        return ResourceType.values();
    }

    /**
     * Retourne les énumérations disponibles pour les types de versions.
     */

    public VersionType[] getVersionTypes() {
        return VersionType.values();
    }

    /**
     * Retourne les énumérations disponibles pour les types de méthodes HTTP.
     */
    public MethodType[] getMethodTypes() {
        return MethodType.values();
    }

    /**
     * Retourne l'instance unique de ConnectorAPI
     */
    public static ConnectorAPI getApi() {
        if (instance == null) {
            instance = new ConnectorAPI();
        }
        return instance;
    }
}
