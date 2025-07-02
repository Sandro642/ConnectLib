package fr.sandro642.github.jobs.misc;

import java.util.HashMap;
import java.util.Map;

public class StoreAndRetrieve {
    /**
     * Création de l'instance de la classe StoreAndRetrieve.
     * @return instance
     */
    private static StoreAndRetrieve instance;

    /**
     * Création de la HashMap qui va stocker les données.
     */
    public final Map<String, Object> store = new HashMap<>();

    /**
     * Création d'une variable clé pour stocker l'URL.
     */
    public final String URL_KEY = "url";

    /**
     * Getter de l'instance de la classe StoreAndRetrieve.
     * @return L'instance de StoreAndRetrieve.
     */
    public static StoreAndRetrieve getInstance() {
        if (instance == null) {
            instance = new StoreAndRetrieve();
        }
        return instance;
    }
}
