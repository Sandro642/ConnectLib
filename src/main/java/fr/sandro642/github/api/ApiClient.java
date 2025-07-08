package fr.sandro642.github.api;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.utils.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Classe ApiClient pour interagir avec l'API de ConnectorAPI.
 * Permet d'effectuer des requêtes GET et POST.
 */

public class ApiClient {

    /**
     * WebClient est utilisé pour effectuer les requêtes HTTP vers l'API.
     * lastResponse stocke la dernière réponse de l'API.
     * logger est utilisé pour enregistrer les informations de débogage et d'erreur.
     */
    private final WebClient webClient;
    private final AtomicReference<ApiResponse<Void>> lastResponse = new AtomicReference<>();
    private final Logger logger = new Logger();

    /**
     * Constructeur de ApiClient qui initialise WebClient avec l'URL de base.
     * @param resourceType Type de ressource (peut être utilisé pour des configurations spécifiques).
     */
    public ApiClient(ResourceType resourceType) {
        String baseUrl = (String) ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().URL_KEY);
        if (baseUrl == null) {
            throw new RuntimeException("URL de base non trouvée. Assurez-vous d'avoir initialisé ConnectorAPI.");
        }

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Méthode pour appeler l'API avec une requête GET.
     * @param routeName
     * @return
     */
    public Mono<ApiResponse<Void>> callAPIGet(String routeName) {
        logger.INFO("Appel GET vers: " + routeName);
        return webClient.get()
                .uri(routeName)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Void>>() {})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(lastResponse::set)
                .doOnError(error -> logger.ERROR("Erreur lors de l'appel GET: " + error.getMessage()));
    }

    /**
     * Méthode pour appeler l'API avec une requête POST.
     * @param routeName
     * @param body Corps de la requête (peut être null pour une requête sans corps).
     * @return
     */
    public Mono<ApiResponse<Void>> callAPIPost(String routeName, Map<String, Object> body) {
        logger.INFO("Appel POST vers: " + routeName);
        return webClient.post()
                .uri(routeName)
                .bodyValue(body != null ? body : Map.of())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Void>>() {})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(lastResponse::set)
                .doOnError(error -> logger.ERROR("Erreur lors de l'appel POST: " + error.getMessage()));
    }
}
