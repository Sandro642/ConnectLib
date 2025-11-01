package fr.sandro642.github.hook;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.enums.ResourceType;
import fr.sandro642.github.enums.lang.CategoriesType;
import fr.sandro642.github.misc.Logger;

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
     * connectLib is an instance of ConnectLib that provides access to the library's configuration and utilities.
     * It is used throughout the HookManager class to log messages and access other functionalities.
     */
    private ConnectLib connectLib = new ConnectLib();

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
     * Returns the current resource type.
     *
     * @return the current resource type
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Sets the file location key based on the resource type.
     * This method updates the store with the file location key based on the resource type.
     * It handles different resource types such as MC_RESOURCES, MAIN_RESOURCES, and TEST_RESOURCES.
     */
    public void FILE_LOCATION_KEY() {
        switch (resourceType) {
            case MC_RESOURCES:
               connectLib.StoreAndRetrieve().store.put(connectLib.StoreAndRetrieve().FILE_LOCATION_KEY, connectLib.MCSupport().getPluginPath());
                break;
            case MAIN_RESOURCES, TEST_RESOURCES:
                connectLib.StoreAndRetrieve().store.put(connectLib.StoreAndRetrieve().FILE_LOCATION_KEY, resourceType.getPath());
                break;


            default:
                connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.HOOKMANAGER_CLASS, "general.unsupportedtype", "type", resourceType.toString()));
        }
    }

    public String BASE_PATH() {
        switch (resourceType) {
            case MC_RESOURCES:
                return connectLib.MCSupport().getPluginPath();

            case MAIN_RESOURCES, TEST_RESOURCES:
                return resourceType.getPath();


            default:
                String message = connectLib.LangManager().getMessage(CategoriesType.HOOKMANAGER_CLASS, "general.unsupportedtype", "type", resourceType.toString());
                connectLib.Logger().CRITICAL(message);
                throw new IllegalArgumentException(message);
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