package fr.sandro642.github.jobs.misc;

/**
 * Enumération pour définir les types de ressources utilisées dans le projet.
 *
 * @author Sandro642
 * @version 1.0
 */

public enum ResourceType {

    /**
     * Gradle and Maven Support.
     */
    MAIN_RESOURCES("src/main/resources"),
    TEST_RESOURCES("src/test/resources");

    /**
     * Prochainement Support : Minecraft
     */
    // MINECRAFT_RESOURCES("src/main/resources/minecraft");


    /**
     * Chemin de la ressource.
     */
    private final String path;

    /**
     * Constructeur pour initialiser le chemin de la ressource.
     *
     * @param path Le chemin de la ressource.
     */
    ResourceType(String path) {
        this.path = path;
    }

    /**
     * Obtient le chemin de la ressource.
     *
     * @return Le chemin de la ressource.
     */
    public String getPath() {
        return path;
    }
}
