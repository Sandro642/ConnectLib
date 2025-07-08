package fr.sandro642.github.jobs.misc;

/**
 * Enumération pour définir les types de ressources utilisées dans le projet.
 *
 * @author Sandro642
 * @version 1.0
 */

public enum ResourceType {
    MAIN_RESOURCES("src/main/resources"),
    TEST_RESOURCES("src/test/resources");

    private final String path;

    ResourceType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
