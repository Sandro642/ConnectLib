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

    private static Logger logger = new Logger();
    private static StoreAndRetrieve storeAndRetrieve = new StoreAndRetrieve();
    private static YamlUtils yamlUtils = new YamlUtils();

    /**
     * Initialise le ConnectorAPI avec le type de ressource spécifié
     */
    public static void initialize(ResourceType resourceType) {
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
}
