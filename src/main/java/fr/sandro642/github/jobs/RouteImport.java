package fr.sandro642.github.jobs;

/**
 * RouteImport is an interface that should be implemented by enums that provide a route.
 * Enums implementing this interface must define the route() method to return their associated route as a String.
 */

public interface RouteImport {

    /**
     * Gets the route associated with the enum constant.
     *
     * @return the route as a String
     */
    String getRoute();
}
