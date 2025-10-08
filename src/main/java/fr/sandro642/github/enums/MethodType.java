package fr.sandro642.github.enums;

/**
 * MethodType is an enumeration representing different HTTP methods used in API requests.
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
