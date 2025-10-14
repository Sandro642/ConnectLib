package fr.sandro642.github.provider;

/**
 * VersionProvider is an interface that provides a method to get the version of a class implementing it.
 * Classes implementing this interface must define the getVersion() method to return their version as a String
 */

public interface VersionProvider {

    /**
     * Gets the version of the class implementing this interface.
     * @return the version as a String
     */
    String getVersion();
}
