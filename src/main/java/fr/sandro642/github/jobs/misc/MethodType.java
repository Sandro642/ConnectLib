package fr.sandro642.github.jobs.misc;

/**
 * Enumération pour définir les types de méthodes HTTP utilisées dans les requêtes API.
 *
 * @author Sandro642
 * @version 1.0
 */

public enum MethodType {
    POST(""),
    GET(""),
    PUT(""),
    PATCH(""),
    DELETE("");

    private final String type;

    MethodType(String typeMethod) {
        this.type = typeMethod;
    }
}
