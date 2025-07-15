package fr.sandro642.github.hook;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.jobs.misc.ResourceType;
import org.bukkit.plugin.Plugin;

/**
 * MCSupport is a utility class for handling Minecraft plugin support in the ConnectLib library.
 * It provides methods to check if the project is a Minecraft project, set and get the plugin instance,
 * @author Sandro642
 * @version 1.0
 */
public class MCSupport {

    /**
     * Instance of MCSupport.
     */
    private static MCSupport instance;

    /**
     * Instance of the Minecraft plugin.
     * This is a singleton instance that should be set in a Minecraft project.
     */
    private Plugin pluginSingleton;

    /**
     * Static block to initialize the singleton instance of MCSupport.
     */
    private MCSupport() {
        // Private constructor to prevent instantiation
    }

    /**
     * Method to check if the current project is a Minecraft project.
     * This method checks if the file location contains the Minecraft resources path.
     * @return true if the project is a Minecraft project, false otherwise.
     */
    public boolean isMCProject() {
        try {

            String fileLocation = (String) ConnectLib.StoreAndRetrieve().store.get(ConnectLib.StoreAndRetrieve().FILE_LOCATION_KEY);
            return fileLocation != null && fileLocation.contains(ResourceType.MC_RESOURCES.getPath());
        } catch (Exception e) {
            ConnectLib.Logger().ERROR("Error while checking if the project is a Minecraft project: " + e.getMessage());
            return false;
        }
    }

    /**
     * Method to set the plugin instance.
     * This method should be called in a Minecraft project to set the plugin instance.
     *
     * @param plugin The instance of the Minecraft plugin to be set.
     * @return The instance of the plugin that was set.
     * @throws IllegalArgumentException If the plugin is null.
     */
    public Plugin setPluginVariable(Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null.");
        }
            this.pluginSingleton = plugin;
            return this.pluginSingleton;
    }

    /**
     * Method to get the path of the plugin's data folder.
     * This method returns the absolute path of the plugin's data folder.
     *
     * @return The absolute path of the plugin's data folder.
     * @throws IllegalStateException If the plugin variable is not set.
     */
    public String getPluginPath() {

        if (pluginSingleton == null) {
            ConnectLib.Logger().ERROR("Plugin variable is not set. Please call setPluginVariable() first.");
        }

        return pluginSingleton.getDataFolder().getAbsolutePath();
    }

    /**
     * Method to check if the plugin has been initialized.
     * This method checks if the pluginSingleton is not null.
     *
     * @return true if the plugin is initialized, false otherwise.
     */
    public boolean isPluginInitialized() {
        return pluginSingleton != null;
    }

    /**
     * Method to get the plugin instance.
     * This method returns the singleton instance of the plugin.
     *
     * @return The instance of the plugin.
     * @throws IllegalStateException If the plugin variable is not set.
     */
    public Plugin getPlugin() {
        return pluginSingleton;
    }

    /**
     * Method to reset the plugin instance.
     * This method sets the pluginSingleton to null, effectively resetting it.
     */
    public void resetPlugin() {
        this.pluginSingleton = null;
    }

    /**
     * Static method to get the singleton instance of MCSupport.
     * This method initializes the instance if it is null and returns it.
     *
     * @return The singleton instance of MCSupport.
     */
    public static MCSupport getInstance() {
        return instance;
    }
}