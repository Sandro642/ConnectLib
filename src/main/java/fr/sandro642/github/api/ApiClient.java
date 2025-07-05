package fr.sandro642.github.api;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.utils.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ApiClient {

    /**
     * WebClient est utilisé pour effectuer des requêtes HTTP de manière réactive.
     * Il est configuré avec l'URL de base récupérée depuis ConnectorAPI.
     * * @see WebClient
     * @see ConnectorAPI
     * @author Sandro642
     * @version 1.0
     */
    private final WebClient webClient;
    private final AtomicReference<ApiResponse<Void>> lastResponse = new AtomicReference<>();

    /**
     * Logger pour les messages d'information, d'avertissement, d'erreur et critique.
     * Il est utilisé pour enregistrer les événements importants dans l'application.
     * @see Logger
     */
    private final Logger logger = ConnectorAPI.Logger();

    /**
     * Constructeur de la classe ApiClient.
     * Initialise le WebClient avec l'URL de base récupérée depuis ConnectorAPI.
     */
    public ApiClient() {
        this.webClient = WebClient.builder()
                .baseUrl(ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().URL_KEY).toString())
                .build();
    }

    /// /// Route API /// ///

    /**
     * Méthode pour appeler l'API avec une requête GET et récupérer une réponse de type ApiResponse<Void>.
     * @param routeName Le nom de la route à appeler.
     * @return Un Mono contenant la réponse de l'API.
     */
    public Mono<ApiResponse<Void>> callAPIGet(String routeName) {
        return webClient.get()
                .uri(routeName)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Void>>() {})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(lastResponse::set);
    }

    /**
     * Méthode pour appeler l'API avec une requête POST et récupérer une réponse de type ApiResponse<Void>.
     * @param routeName Le nom de la route à appeler.
     * @param body Le corps de la requête sous forme de Map.
     * @return Un Mono contenant la réponse de l'API.
     */
    public Mono<ApiResponse<Void>> callAPIPost(String routeName, Map<String, Object> body) {
        return webClient.post()
                .uri(routeName)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<Void>>() {})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(lastResponse::set);
    }
}
