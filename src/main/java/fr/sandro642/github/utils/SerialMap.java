package fr.sandro642.github.utils;

import fr.sandro642.github.ConnectorAPI;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
    public void saveData(HashMap<String, Object> map, String fileName, Plugin plugin) {
        try {
            File file;

            if (plugin != null) {
                file = new File(plugin.getDataFolder(), fileName);
            } else {
                file = new File(fileName);
            }

            System.out.println("=== SAUVEGARDE SERIALMAP ===");
            System.out.println("Fichier destination: " + file.getAbsolutePath());
            System.out.println("Map à sauvegarder: " + map);
            System.out.println("Taille de la map: " + map.size());

            // Vérifier le contenu avant sauvegarde
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println("Avant sauvegarde - Clé: '" + entry.getKey() +
                        "' -> Valeur: '" + entry.getValue() + "'");
            }

            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(map);
            outputStream.close();

            System.out.println("✓ Sauvegarde réussie dans " + fileName);
            System.out.println("Taille du fichier créé: " + file.length() + " bytes");

            // Test de relecture immédiate pour vérifier
            System.out.println("=== TEST DE RELECTURE IMMÉDIATE ===");
            ObjectInputStream testInput = new ObjectInputStream(new FileInputStream(file));
            HashMap<String, Object> testMap = (HashMap<String, Object>) testInput.readObject();
            testInput.close();

            System.out.println("Données relues immédiatement: " + testMap);
            for (Map.Entry<String, Object> entry : testMap.entrySet()) {
                System.out.println("Après relecture - Clé: '" + entry.getKey() +
                        "' -> Valeur: '" + entry.getValue() + "'");
            }

        } catch (Exception e) {
            System.out.println("✗ Erreur sauvegarde " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour charger une HashMap depuis un fichier.
     *
     * @param fileName Le nom du fichier à partir duquel la HashMap sera chargée.
     * @return La HashMap chargée, ou une nouvelle HashMap vide en cas d'erreur.
     */
    public HashMap<String, Object> loadData(String fileName, Plugin plugin) {
        try {
            File file;

            if (plugin != null) {
                file = new File(plugin.getDataFolder(), fileName);
            } else {
                file = new File(fileName);
            }

            // Debug: vérifier l'existence du fichier
            System.out.println("Tentative de chargement du fichier: " + file.getAbsolutePath());
            System.out.println("Fichier existe: " + file.exists());
            System.out.println("Fichier lisible: " + file.canRead());
            System.out.println("Taille du fichier: " + file.length() + " bytes");

            if (!file.exists()) {
                System.out.println("ERREUR: Le fichier n'existe pas!");
                return new HashMap<>();
            }

            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            HashMap<String, Object> data = (HashMap<String, Object>) inputStream.readObject();
            inputStream.close();

            System.out.println("✓ Chargement réussi depuis " + fileName);
            System.out.println("Données chargées: " + data);

            return data;
        } catch (FileNotFoundException e) {
            System.out.println("✗ Fichier introuvable: " + fileName);
            e.printStackTrace();
            return new HashMap<>();
        } catch (IOException e) {
            System.out.println("✗ Erreur de lecture du fichier: " + fileName);
            e.printStackTrace();
            return new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.out.println("✗ Erreur de désérialisation: " + fileName);
            e.printStackTrace();
            return new HashMap<>();
        } catch (Exception e) {
            System.out.println("✗ Erreur générale lors du chargement: " + fileName);
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * Méthode pour charger une HashMap depuis un fichier sans spécifier de plugin.
     *
     * @param fileName Le nom du fichier à partir duquel la HashMap sera chargée.
     * @return La HashMap chargée, ou une nouvelle HashMap vide en cas d'erreur.
     */
    public HashMap<String, Object> loadData(String fileName) {
        return loadData(fileName, null);
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
