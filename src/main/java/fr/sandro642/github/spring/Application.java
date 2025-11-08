package fr.sandro642.github.spring;

import fr.sandro642.github.ConnectLib;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;

/**
 * Main application class for starting the Spring Boot application.
 */

@SpringBootApplication
public class Application {

    // Singleton instance
    private static Application instance;

    private static final ConnectLib connectLib = new ConnectLib();

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
            props.put("server.port", connectLib.StoreAndRetrieve().get(connectLib.StoreAndRetrieve().PORT)); // définit le port du serveur
            props.put("logging.level.root", "OFF");           // coupe l'affichage des logs
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
