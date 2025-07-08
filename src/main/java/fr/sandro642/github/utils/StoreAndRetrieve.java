package fr.sandro642.github.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class StoreAndRetrieve {

    public final String URL_KEY = "baseUrl";
    public final String FILE_LOCATION_KEY = "fileLocation";
    public final Map<String, Object> store = new ConcurrentHashMap<>();

    /**
     * Stocke une valeur avec une clé
     */
    public void put(String key, Object value) {
        store.put(key, value);
    }

    /**
     * Récupère une valeur par sa clé
     */
    public Object get(String key) {
        return store.get(key);
    }

    /**
     * Supprime une valeur par sa clé
     */
    public void remove(String key) {
        store.remove(key);
    }

    /**
     * Vérifie si une clé existe
     */
    public boolean containsKey(String key) {
        return store.containsKey(key);
    }
}
