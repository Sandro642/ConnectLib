package fr.sandro642.github.hook;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.jobs.misc.ResourceType;
import org.bukkit.plugin.Plugin;

/**
 * MCSupport est une classe utilitaire pour gérer les hooks liés à Minecraft.
 * Elle permet de vérifier si le projet est un projet Minecraft et de gérer les ressources associées.
 * @author Sandro642
 * @version 1.0
 */
public class MCSupport {

    /**
     * Création de l'instance unique de MCSupport.
     */
    private static MCSupport instance;

    /**
     * Instance unique du plugin Minecraft.
     * Utilisée pour stocker la variable du plugin Minecraft.
     */
    private Plugin pluginSingleton;

    /**
     * Constructeur privé pour empêcher l'instanciation directe.
     */
    private MCSupport() {
        // Constructeur privé pour le pattern Singleton
    }

    /**
     * Méthode permettant de vérifier si le projet est un projet Minecraft.
     * Elle vérifie si le chemin du fichier de configuration contient le chemin des ressources Minecraft.
     *
     * @return true si c'est un projet Minecraft, false sinon.
     */
    public boolean isMCProject() {
        try {

            String fileLocation = (String) ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().FILE_LOCATION_KEY);
            return fileLocation != null && fileLocation.contains(ResourceType.MC_RESOURCES.getPath());
        } catch (Exception e) {
            // En cas d'erreur, considérer que ce n'est pas un projet MC
            return false;
        }
    }

    /**
     * Méthode permettant de définir la variable du plugin Minecraft.
     * Elle doit être appelée dans un projet Minecraft pour initialiser le plugin.
     *
     * @param plugin Le plugin Minecraft à définir.
     * @return L'instance du plugin définie.
     * @throws IllegalStateException    Si la méthode n'est pas appelée dans un projet Minecraft.
     * @throws IllegalArgumentException Si le plugin fourni est null.
     */
    public Plugin setPluginVariable(Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null.");
        }
            this.pluginSingleton = plugin;
            return this.pluginSingleton;
    }

    /**
     * Méthode permettant d'obtenir le chemin du path du plugin Minecraft.
     * Elle doit être appelée dans un projet Minecraft pour récupérer le chemin du dossier de données du plugin.
     *
     * @return Le chemin du dossier de données du plugin.
     * @throws IllegalStateException Si la méthode n'est pas appelée dans un projet Minecraft ou si le plugin n'est pas initialisé.
     */
    public String getPluginPath() {

        if (pluginSingleton == null) {
            throw new IllegalStateException("Plugin variable is not set. Please call setPluginVariable first.");
        }

        return pluginSingleton.getDataFolder().getAbsolutePath();
    }

    /**
     * Méthode permettant de vérifier si le plugin est initialisé.
     *
     * @return true si le plugin est initialisé, false sinon.
     */
    public boolean isPluginInitialized() {
        return pluginSingleton != null;
    }

    /**
     * Méthode permettant d'obtenir l'instance du plugin (si initialisé).
     *
     * @return L'instance du plugin ou null si non initialisé.
     */
    public Plugin getPlugin() {
        return pluginSingleton;
    }

    /**
     * Méthode permettant de réinitialiser l'instance du plugin.
     * Utile pour les tests ou le rechargement.
     */
    public void resetPlugin() {
        this.pluginSingleton = null;
    }

    /**
     * Méthode permettant d'obtenir l'instance unique de MCSupport.
     *
     * @return L'instance unique de MCSupport.
     */
    public static MCSupport getInstance() {
        if (instance == null) {
            synchronized (MCSupport.class) {
                if (instance == null) {
                    instance = new MCSupport();
                }
            }
        }
        return instance;
    }
}