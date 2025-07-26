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
 * AnnotHandler is a class that manages the discovery and execution of listeners annotated with @AnnotConnect.
 * It scans the package for classes that implement the ConnectListener interface and invokes methods annotated with @AnnotConnect.
 *
 * @author Sandro642
 * @version 1.0
 * @see ConnectListener
 * @see AnnotConnect
 */

public class AnnotHandler {

    /**
     * AnnotConnect is an annotation used to mark methods that should be executed when a connection event occurs.
     * Methods annotated with this annotation will be invoked by the AnnotHandler when it discovers them.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface AnnotConnect { }

    /**
     * ConnectListener is an interface that should be implemented by classes that want to listen for connection events.
     * Classes implementing this interface can have methods annotated with @AnnotConnect to handle specific connection events.
     */
    interface ConnectListener {
        <T> void onDebug(T type);
    }

    /**
     * listeners is a list that holds instances of classes implementing the ConnectListener interface.
     * It is populated by the discoverListeners method, which scans the package for classes that implement ConnectListener.
     * The methods of these classes can be annotated with @AnnotConnect to indicate that they should be executed when a connection event occurs.
     */
    private final List<ConnectListener> listeners = new LinkedList<>();

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
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during listener discovery", e);
        }
    }

    /**
     * getClassesInPackage is a helper method that retrieves all classes in a specified package.
     * It uses the class loader to find resources in the package and loads them as Class objects.
     *
     * @param packageName The name of the package to scan for classes.
     * @return A list of Class objects found in the specified package.
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
     * execListener is a method that executes all methods annotated with @AnnotConnect in the discovered listeners.
     * It first checks if the listeners list is empty and calls discoverListeners if it is.
     * Then, it iterates through each listener and invokes the annotated methods.
     */
    public void execListener(){
        if (listeners.isEmpty()) {
            discoverListeners();
        }

        for(ConnectListener listener : listeners){
            for(Method method : listener.getClass().getDeclaredMethods()){
                if(method.isAnnotationPresent(AnnotConnect.class)) {
                    try {
                        method.setAccessible(true);
                        method.invoke(listener);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * Main method to execute the AnnotHandler and trigger the execution of annotated methods.
     * This method serves as an entry point for testing the AnnotHandler functionality.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        AnnotHandler annotLogic = new AnnotHandler();
        annotLogic.execListener();
    }
}