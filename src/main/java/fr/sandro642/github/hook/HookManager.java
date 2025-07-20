package fr.sandro642.github.hook;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.enums.ResourceType;
import fr.sandro642.github.utils.Logger;

/**
 * HookManager is a class that manages hooks for different resource types.
 * It allows for the initialization and management of hooks based on the specified resource type.
 * @see HookManager#BASE_PATH()
 * @see HookManager#FILE_LOCATION_KEY()
 * @author Sandro642
 * @version 1.0
 */

public class HookManager {

    /**
     * Singleton instance of HookManager.
     * This instance is used to manage hooks for different resource types.
     */
    private static HookManager instance;

    /**
     * Logger instance for logging messages.
     * This logger is used to log errors and other messages related to the hook management.
     */
    private static Logger logger = new Logger();

    /**
     * Initializes the HookManager with the specified resource type.
     *
     * @param resourceType the type of resource to initialize the hook for
     */
    private static ResourceType resourceType;

    /**
     * Initializes the hook for the specified resource type.
     * * This method sets the resource type for the hook manager and returns the initialized resource type.
     *
     * @param resourceType
     * @return the initialized resource type
     */
    public ResourceType initHook(ResourceType resourceType) {
        this.resourceType = resourceType;
        return this.resourceType;
    }

    /**
     * Sets the file location key based on the resource type.
     * This method updates the store with the file location key based on the resource type.
     * It handles different resource types such as MC_RESOURCES, MAIN_RESOURCES, and TEST_RESOURCES.
     */
    public void FILE_LOCATION_KEY() {
        switch (resourceType) {
            case MC_RESOURCES:
               ConnectLib.StoreAndRetrieve().store.put(ConnectLib.StoreAndRetrieve().FILE_LOCATION_KEY, ConnectLib.MCSupport().getPluginPath());
                break;
            case MAIN_RESOURCES, TEST_RESOURCES:
                ConnectLib.StoreAndRetrieve().store.put(ConnectLib.StoreAndRetrieve().FILE_LOCATION_KEY, resourceType.getPath());
                break;


            default:
                logger.CRITICAL("Unsupported resource type: " + resourceType);
        }
    }

    public String BASE_PATH() {
        switch (resourceType) {
            case MC_RESOURCES:
                return ConnectLib.MCSupport().getPluginPath();

            case MAIN_RESOURCES, TEST_RESOURCES:
                return resourceType.getPath();


            default:
                logger.CRITICAL("Unsupported resource type: " + resourceType);
                return null;
        }
    }

    /**
     * Returns the singleton instance of HookManager.
     * This method ensures that only one instance of HookManager is created and returned.
     *
     * @return the singleton instance of HookManager
     */
    public static HookManager getInstance() {
        if (instance == null) {
            instance = new HookManager();
        }
        return instance;
    }
}