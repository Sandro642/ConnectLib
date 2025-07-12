package fr.sandro642.github.utils;

import java.util.HashMap;

/**
 * StoreAndRetrieve est une classe utilitaire pour stocker et récupérer des valeurs associées à des clés.
 * Elle utilise une map concurrente pour assurer la sécurité des threads lors de l'accès aux données.
 *
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class StoreAndRetrieve {

    /**
     * Clés utilisées pour stocker des valeurs dans la map.
     * * URL_KEY : Clé pour stocker l'URL de base de l'API.
     * * FILE_LOCATION_KEY : Clé pour stocker le chemin du fichier de configuration.
     * * Ces clés sont utilisées pour accéder aux valeurs stockées dans la map.
     */
    public final String URL_KEY = "baseUrl";
    public final String FILE_LOCATION_KEY = "fileLocation";
    public final HashMap<String, Object> store = new HashMap<>();

    /**
     * Ajoute une valeur à la map avec la clé spécifiée.
     * @param key est la valeur de la clé pour laquelle la valeur sera stockée.
     * @param value est la valeur à stocker dans la map.
     */
    public void put(String key, Object value) {
        store.put(key, value);
    }

    /**
     * Récupère une valeur associée à la clé spécifiée.
     * @param key est la clé pour laquelle la valeur sera récupérée.
     * @return la valeur associée à la clé, ou null si la clé n'existe pas.
     */
    public Object get(String key) {
        return store.get(key);
    }


    /**
     * Supprime une valeur associée à la clé spécifiée.
     * @param key est la clé pour laquelle la valeur sera supprimée.
     */
    public void remove(String key) {
        store.remove(key);
    }


    /**
     * Vérifie si la map contient une clé spécifiée.
     * @param key est la clé à vérifier dans la map.
     * @return true si la clé existe dans la map, false sinon.
     */
    public boolean containsKey(String key) {
        return store.containsKey(key);
    }
}
