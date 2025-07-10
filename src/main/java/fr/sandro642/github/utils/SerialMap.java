package fr.sandro642.github.utils;

import fr.sandro642.github.ConnectorAPI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Classe utilitaire pour gérer les cartes de sérialisation.
 * Actuellement vide, mais peut être étendue pour inclure des méthodes de sérialisation/désérialisation.
 *
 * @author Sandro642
 * @version 1.0
 */

public class SerialMap {

    /**
     * Instance de la classe serialMap.
     */
    private static SerialMap instance;

    /**
     * Constructeur privé pour empêcher l'instanciation directe.
     */
    private SerialMap() {
        // Constructeur privé pour le pattern Singleton
    }

    /**
     * Méthode pour sauvegarder une HashMap dans un fichier.
     *
     * @param map      La HashMap à sauvegarder.
     * @param fileName Le nom du fichier dans lequel la HashMap sera sauvegardée.
     */
    public void saveData(HashMap<String, Object> map, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            outputStream.writeObject(map);
            outputStream.close();
            //ConnectorAPI.Logger().INFO("✓ Sauvegarde réussie dans " + fileName);
        } catch (Exception e) {
            ConnectorAPI.Logger().ERROR("✗ Erreur sauvegarde " + fileName);
        }
    }

    /**
     * Méthode pour charger une HashMap depuis un fichier.
     *
     * @param fileName Le nom du fichier à partir duquel la HashMap sera chargée.
     * @return La HashMap chargée, ou une nouvelle HashMap vide en cas d'erreur.
     */
    public HashMap<String, Object> loadData(String fileName) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
            HashMap<String, Object> data = (HashMap<String, Object>) inputStream.readObject();
            inputStream.close();
            //ConnectorAPI.Logger().INFO("✓ Chargement réussi depuis " + fileName);
            return data;
        } catch (Exception e) {
            ConnectorAPI.Logger().ERROR("✗ Erreur chargement " + fileName);
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * Méthode pour obtenir l'instance unique de serialMap.
     *
     * @return L'instance unique de serialMap.
     */
    public static SerialMap getInstance() {
        if (instance == null) {
            instance = new SerialMap();
        }
        return instance;
    }
}
