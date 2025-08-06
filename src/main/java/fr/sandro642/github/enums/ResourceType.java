package fr.sandro642.github.enums;

/**
 * ResourceType is an enumeration representing different types of resources in the ConnectLib library.
 *
 * @author Sandro642
 * @version 1.0
 */

public enum ResourceType {

    /**
     * Gradle and Maven Support.
     */
    MAIN_RESOURCES("src/main/data"),
    TEST_RESOURCES("src/test/resources"),
    MC_RESOURCES("");


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
