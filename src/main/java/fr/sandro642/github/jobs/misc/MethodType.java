package fr.sandro642.github.jobs.misc;

/**
 * Enumération pour définir les types de méthodes HTTP utilisées dans les requêtes API.
 *
 * @author Sandro642
 * @version 1.0
 */

public enum MethodType {
    POST(true),
    GET(false);

    private final Boolean type;

    MethodType(Boolean typeMethod) {
        this.type = typeMethod;
    }

    public Boolean getMethod() {
        return type;
    }
}
