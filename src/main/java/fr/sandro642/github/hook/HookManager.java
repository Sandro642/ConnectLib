package fr.sandro642.github.hook;

import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.utils.SerialMap;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

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
        if (usedType == ResourceType.MC_RESOURCES) {
            Plugin pluginTarget = MCSupport.getInstance().getPlugin();

            SerialMap.getInstance().saveData(map, fileName, pluginTarget);
        } else {
            SerialMap.getInstance().saveData(map, fileName);
        }
    }

    /**
     * Méthode pour charger une HashMap depuis un fichier.
     *
     * @param fileName
     */
    public HashMap<String, Object> loadData(String fileName) {
        if (usedType == ResourceType.MC_RESOURCES) {
            Plugin pluginTarget = MCSupport.getInstance().getPlugin();
            return SerialMap.getInstance().loadData(fileName, pluginTarget);
        } else {
            return SerialMap.getInstance().loadData(fileName);
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
