package fr.sandro642.github.misc;

import java.util.HashMap;

/**
 * StoreAndRetrieve is a utility class for storing and retrieving key-value pairs.
 * It provides methods to add, get, remove, and check for keys in a map.
 * * This class is used to manage configuration values such as the base URL of an API and file locations.
 *
 * @author Sandro642
 * @version 1.0
 */

public class StoreAndRetrieve {

    /**
     * URL_KEY is the key used to store the base URL of the API.
     * FILE_LOCATION_KEY is the key used to store the file location for resources.
     * store is a HashMap that holds key-value pairs for configuration settings.
     */
    public final String URL_KEY = "baseUrl";
    public final String FILE_LOCATION_KEY = "fileLocation";

    public final String NAME_DASHBOARD = "nameDashboard";
    public final String PORT = String.valueOf(3000);
    public final String DYNAMIC_PORT = "dynamicPort";
    public final HashMap<String, Object> store = new HashMap<>();

    /**
     * Add a key-value pair to the store.
     * @param key is the key under which the value will be stored.
     * @param value is the value to be stored under the specified key.
     */
    public void put(String key, Object value) {
        store.put(key, value);
    }

    /**
     * Retrieve a value associated with the specified key.
     * @param key is the key for which the value will be retrieved.
     * @return the value associated with the specified key, or null if the key does not exist.
     */
    public Object get(String key) {
        return store.get(key);
    }

    /**
     * Remove a key-value pair from the store.
     * @param key is the key of the entry to be removed.
     */
    public void remove(String key) {
        store.remove(key);
    }

    /**
     * Check if the store contains a specific key.
     * @param key is the key to check for existence in the store.
     * @return true if the key exists, false otherwise.
     */
    public boolean containsKey(String key) {
        return store.containsKey(key);
    }
}
