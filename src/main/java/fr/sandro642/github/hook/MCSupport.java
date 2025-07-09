package fr.sandro642.github.hook;

import org.bukkit.plugin.Plugin;

/**
 * MCSupport est une classe utilitaire pour gérer les hooks liés à Minecraft.
 * Elle permet de vérifier si le projet est un projet Minecraft et de gérer les ressources associées.
 * @author Sandro642
 * @version 1.0
 */

public class MCSupport {

    /**
     * Instance unique du plugin Minecraft.
     * Utilisée pour stocker la variable du plugin Minecraft.
     */
    private Plugin pluginSingleton;

    /**
     * Méthode permettant de vérifier si le projet est un projet Minecraft.
     * Elle vérifie si le chemin du fichier de configuration contient le chemin des ressources Minecraft.
     * @return true si c'est un projet Minecraft, false sinon.
     */
    public boolean isMCProject() {
        String fileLocation = (String) fr.sandro642.github.ConnectorAPI.StoreAndRetrieve().store.get(fr.sandro642.github.ConnectorAPI.StoreAndRetrieve().FILE_LOCATION_KEY);
        return fileLocation != null && fileLocation.contains(fr.sandro642.github.jobs.misc.ResourceType.MC_RESOURCES.getPath());
    }

    /**
     * Méthode permettant de définir la variable du plugin Minecraft.
     * Elle doit être appelée dans un projet Minecraft pour initialiser le plugin.
     * @param plugin Le plugin Minecraft à définir.
     * @return L'instance du plugin définie.
     */
    private Plugin setPluginVariable(Plugin plugin) {
        if (isMCProject()) {
            return pluginSingleton = plugin;
        } else {
            throw new IllegalStateException("This method can only be called in a Minecraft project.");
        }
    }

    /**
     * Méthode permettant d'obtenir le chemin du path du plugin Minecraft.
     * Elle doit être appelée dans un projet Minecraft pour récupérer le chemin du dossier de données du plugin.
     * @return Le chemin du dossier de données du plugin.
     */
    private String getPluginPath() {
        if (isMCProject()) {
            if (pluginSingleton == null) {
                throw new IllegalStateException("Plugin variable is not set. Please call setPluginVariable first.");
            }
            return pluginSingleton.getDataFolder().getAbsolutePath();
        } else {
            throw new IllegalStateException("This method can only be called in a Minecraft project.");
        }
    }

}
