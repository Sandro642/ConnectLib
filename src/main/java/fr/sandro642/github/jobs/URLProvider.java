package fr.sandro642.github.jobs;

/**
 * URLProvider is an interface that provides a method to get the URL of a class implementing it.
 * Classes implementing this interface must define the getURL() method to return their URL as a String
 */

public interface URLProvider {

    /**
     * Gets the URL of the class implementing this interface.
     * @return the URL as a String
     */
    String getURL();
}
