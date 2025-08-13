package fr.sandro642.github.annotations;

import fr.sandro642.github.ConnectLib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * AnnotHandler is a class that manages the discovery and execution of listeners annotated with @DisplayLogs.
 * It scans the package for classes that implement the ConnectListener interface and invokes methods annotated with @DisplayLogs.
 *
 * @author Sandro642
 * @version 1.0
 * @see ConnectListener
 */
public class AnnotHandler {

    /**
     * DisplayLogs is an annotation used to mark methods that should display all logs during execution.
     * Methods annotated with this annotation will show all INFO, WARN, ERROR, and CRITICAL logs.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface DisplayLogs { }

    /**
     * ConnectListener is an interface that should be implemented by classes that want to listen for connection events.
     * Classes implementing this interface can have methods annotated with @DisplayLogs to handle specific connection events.
     */
    public interface ConnectListener {}

    /**
     * listeners is a list that holds instances of classes implementing the ConnectListener interface.
     */
    private final List<ConnectListener> listeners = new LinkedList<>();

    /**
     * Flag to control whether logs should be displayed or not
     */
    private static boolean logsEnabled = false;

    /**
     * Enable logs display - called automatically when executing @DisplayLogs methods
     */
    public static void enableLogs() {
        logsEnabled = true;
    }

    /**
     * Disable logs display
     */
    public static void disableLogs() {
        logsEnabled = false;
    }

    /**
     * Check if logs are currently enabled
     */
    public static boolean areLogsEnabled() {
        return logsEnabled;
    }

    /**
     * discoverListeners is a method that scans the package of the AnnotHandler class for classes that implement the ConnectListener interface.
     * It creates instances of these classes and adds them to the listeners list.
     */
    private void discoverListeners() {
        try {
            String packageName = this.getClass().getPackage().getName();
            List<Class<?>> classes = getClassesInPackage(packageName);

            for (Class<?> clazz : classes) {
                if (ConnectListener.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                    try {
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        listeners.add((ConnectListener) instance);
                    } catch (Exception e) {
                        // Silent fail during discovery
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during listener discovery", e);
        }
    }

    /**
     * getClassesInPackage is a helper method that retrieves all classes in a specified package.
     */
    private List<Class<?>> getClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            URL resource = classLoader.getResource(path);

            if (resource != null) {
                File directory = new File(resource.getFile());
                if (directory.exists()) {
                    for (File file : directory.listFiles()) {
                        if (file.getName().endsWith(".class")) {
                            String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                            try {
                                classes.add(Class.forName(className));
                            } catch (ClassNotFoundException e) {
                                // Ignore classes that can't be loaded
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            ConnectLib.Logger().WARN("Failed to retrieve classes in package: " + packageName + ". Exception: " + e.getMessage());
        }
        return classes;
    }

    /**
     * execListener executes all methods annotated with @DisplayLogs in the discovered listeners.
     * It enables log display for methods with @DisplayLogs annotation.
     */
    public void execListener(){
        if (listeners.isEmpty()) {
            discoverListeners();
        }

        for(ConnectListener listener : listeners){
            for(Method method : listener.getClass().getDeclaredMethods()){
                if(method.isAnnotationPresent(DisplayLogs.class)) {
                    try {
                        // Enable logs for this method execution
                        enableLogs();

                        method.setAccessible(true);
                        method.invoke(listener);

                        // Keep logs enabled after execution (you can change this behavior if needed)
                        // disableLogs();

                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * Method to manually execute a specific method with @DisplayLogs annotation
     * Useful for static methods or methods outside of ConnectListener classes
     */
    public static void executeWithLogs(Runnable method) {
        enableLogs();
        try {
            method.run();
        } finally {
            // Optionally disable logs after execution
            // disableLogs();
        }
    }

    /**
     * Main method to execute the AnnotHandler and trigger the execution of annotated methods.
     */
    public static void main(String[] args) {
        AnnotHandler annotLogic = new AnnotHandler();
        annotLogic.execListener();
    }
}