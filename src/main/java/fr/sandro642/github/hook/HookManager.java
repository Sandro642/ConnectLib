package fr.sandro642.github.hook;

import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.utils.SerialMap;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe HookManager pour gérer les hooks dans le projet.
 *
 * @author Sandro642
 * @version 1.0
 */

public class HookManager {
    /**
     * Instance de la classe HookManager.
     */
    private static HookManager instance;

    /**
     * Constructeur privé pour empêcher l'instanciation directe.
     */
    private HookManager() {
        // Constructeur privé pour le pattern Singleton
    }

    /**
     * Type de ressource utilisé par le HookManager.
     */
    private ResourceType usedType;

    /**
     * Obtient le type de ressource utilisé par le HookManager.
     *
     * @return Le type de ressource utilisé.
     */
    public ResourceType getUsedType() {
        return usedType;
    }

    /**
     * Méthode pour choisir le type de ressource utilisé par le HookManager.
     *
     * @param type
     * @return
     */
    public ResourceType TypeManagerHook(ResourceType type) {
        this.usedType = type;
        return this.usedType;
    }

    /**
     * Méthode pour sauvegarder une HashMap dans un fichier.
     *
     * @param map
     * @param fileName
     */
    public void saveData(HashMap<String, Object> map, String fileName) {
        // DEBUG: Voir ce qui est passé à la sauvegarde
        System.out.println("=== DÉBUT SAUVEGARDE ===");
        System.out.println("fileName: " + fileName);
        System.out.println("map reçue: " + map);
        System.out.println("map.size(): " + map.size());
        System.out.println("map.keySet(): " + map.keySet());

        // Détailler chaque entrée
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("Clé: '" + key + "' -> Valeur: '" + value + "' (type: " +
                    (value != null ? value.getClass().getSimpleName() : "null") + ")");
        }

        if (usedType == ResourceType.MC_RESOURCES) {
            Plugin pluginTarget = MCSupport.getInstance().getPlugin();
            System.out.println("usedType: " + usedType + " | pluginTarget: " + pluginTarget);

            SerialMap.getInstance().saveData(map, fileName, pluginTarget);
        } else {
            SerialMap.getInstance().saveData(map, fileName, null);
        }

        System.out.println("=== FIN SAUVEGARDE ===");
    }

    /**
     * Méthode pour charger une HashMap depuis un fichier.
     *
     * @param fileName
     * @return La HashMap chargée, ou une nouvelle HashMap vide en cas d'erreur.
     */
    public HashMap<String, Object> loadData(String fileName) {
        if (usedType == ResourceType.MC_RESOURCES) {
            Plugin pluginTarget = MCSupport.getInstance().getPlugin();

            System.out.println("usedType: " + usedType + " | pluginTarget: " + pluginTarget);
            System.out.println("fileName demandé: " + fileName);

            HashMap<String, Object> result = SerialMap.getInstance().loadData(fileName, pluginTarget);

            // Debug: afficher le contenu récupéré
            System.out.println("Données récupérées: " + result);
            System.out.println("Taille de la HashMap: " + result.size());
            System.out.println("Clés disponibles: " + result.keySet());

            return result;
        } else {
            HashMap<String, Object> result = SerialMap.getInstance().loadData(fileName);

            // Debug: afficher le contenu récupéré
            System.out.println("Données récupérées (sans plugin): " + result);
            System.out.println("Taille de la HashMap: " + result.size());

            return result;
        }
    }

    /**
     * Méthode pour obtenir l'instance unique de HookManager.
     * @return
     */
    public static HookManager getInstance() {
        if (instance == null) {
            instance = new HookManager();
        }
        return instance;
    }
}
