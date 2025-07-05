package fr.sandro642.github;

/**
 * ConnectorAPI est une librairie Java permettant de créer un lien de communication entre son projet Java et une API Rest Fastify.
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 * @see ConnectorAPI#JobTemplate()
 * @see ConnectorAPI#JobGetRoutes()
 */

public class ConnectorAPI {

    // Création de Getter de chaque classe de la librairie

    /**
     * Retourne l'instance de la classe JobTemplate.
     *
     * @return L'instance de JobTemplate.
     */
    public static fr.sandro642.github.jobs.JobTemplate JobTemplate() {
        return fr.sandro642.github.jobs.JobTemplate.getInstance();
    }

    /**
     * Retourne l'instance de la classe JobGetInfos.
     *
     * @return L'instance de JobGetRoutes.
     */
    public static fr.sandro642.github.jobs.JobGetInfos JobGetInfos() {
        return fr.sandro642.github.jobs.JobGetInfos.getInstance();
    }

    /**
     * Retourne l'instance de la classe StoreAndretrieve.
     *
     * @return L'instance de StoreAndRetrieve.
     */
    public static fr.sandro642.github.jobs.misc.StoreAndRetrieve StoreAndRetrieve() {
        return fr.sandro642.github.jobs.misc.StoreAndRetrieve.getInstance();
    }

    /**
     * Retourne l'instance de la classe Logger.
     *
     * @return L'instance de Logger.
     */
    public static fr.sandro642.github.utils.Logger Logger() {
        return new fr.sandro642.github.utils.Logger();
    }
}
