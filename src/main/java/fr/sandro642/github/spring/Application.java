package fr.sandro642.github.spring;

import fr.sandro642.github.ConnectLib;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;

/**
 * Main application class for starting the Spring Boot application.
 */

@SpringBootApplication
public class Application implements WebFluxConfigurer {

    // Singleton instance
    private static Application instance;

    /**
     * ConnectLib instance for managing connections.
     */
    private static final ConnectLib connectLib = new ConnectLib();

    /**
     * Determines the port to use for the server, checking for a dynamic port first.
     * @return the port number as a string
     */
    private String Dyn_Port() {
        if (connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().DYNAMIC_PORT) != null) {
            return connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().DYNAMIC_PORT).toString();
        } else {
            return connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().PORT).toString();
        }
    }

    /**
     * Checks if the application is using a static port.
     * @return true if using a static port, false if using a dynamic port
     */
    private boolean isStatic() {
        if (connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().DYNAMIC_PORT) != null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Starts the Spring Boot application in a non-blocking manner.
     * @return a Mono that completes when the application has started
     */
    public Mono<Void> startApplication() {
        return Mono.fromRunnable(() -> {
            SpringApplication app = new SpringApplication(Application.class);
            app.setBannerMode(Banner.Mode.OFF);               // supprime le banner Spring
            app.setLogStartupInfo(false);                     // désactive l'info de démarrage
            Map<String, Object> props = new HashMap<>();
            props.put("server.port", Dyn_Port()); // définit le port du serveur
            props.put("logging.level.root", "OFF");
            app.setDefaultProperties(props);
            app.run();

        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    /**
     * Gets the singleton instance of the Application class.
     * @return the singleton Application instance
     */
    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }
}
