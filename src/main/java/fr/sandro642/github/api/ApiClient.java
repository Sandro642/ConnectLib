package fr.sandro642.github.api;

import fr.sandro642.github.ConnectorAPI;
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

public class ApiClient extends ApiFactory {

  /**
   * WebClient est utilisé pour effectuer les requêtes HTTP vers l'API.
   * lastResponse stocke la dernière réponse de l'API.
   * Logger est utilisé pour enregistrer les informations de débogage et d'erreur.
   */
  private final WebClient webClient;

  private final AtomicReference<ApiFactory> lastResponse = new AtomicReference<>();

  private final Logger logger = new Logger();
  private final ApiFactory response = new ApiFactory();

  /**
   * Constructeur de ApiClient qui initialise WebClient avec l'URL de base.
   */
  public ApiClient() {
    String baseUrl = (String) ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().URL_KEY);

    if (baseUrl == null) {
      throw new RuntimeException("URL de base non trouvée. Assurez-vous d'avoir initialisé ConnectorAPI. ");
    }

    this.webClient = WebClient.builder()
        .baseUrl(baseUrl)
        .build();
  }

  /**
   * Méthode pour appeler l'API avec une requête GET.
   * 
   * @param routeName c'est le nom de la route à appeler.
   * @return la réponse de l'API encapsulée.
   */
  public Mono<ApiFactory> callAPIGet(String routeName) {
    logger.INFO("Appel GET vers: " + routeName);
    return webClient.get()
            .uri(routeName)
            .retrieve()
            .bodyToMono(String.class)  // Récupère le JSON brut comme String
            .subscribeOn(Schedulers.boundedElastic())
            .map(rawJson -> {
              response.parseFromRawJson(rawJson);
              return response;
            })
            .doOnNext(lastResponse::set)
            .doOnError(error -> logger.CRITICAL("Erreur lors de l'appel GET: " + error.getMessage()));
  }


  /**
   * Méthode pour appeler l'API avec une requête POST.
   * 
   * @param routeName Nom de la route à appeler.
   * @param body      Corps de la requête (peut être null pour une requête sans
   *                  corps).
   * @return la réponse de l'API encapsulée.
   */
  public Mono<ApiFactory> callAPIPost(String routeName, Map<String, Object> body) {
    logger.INFO("Appel POST vers: " + routeName);
    return webClient.post()
        .uri(routeName)
        .bodyValue(body != null ? body : Map.of())
        .retrieve()
        .bodyToMono(String.class)
        .subscribeOn(Schedulers.boundedElastic())
            .map(rawJson -> {
              response.parseFromRawJson(rawJson);
              return response;
            })
            .doOnNext(lastResponse::set)
        .doOnError(error -> logger.CRITICAL("Erreur lors de l'appel POST: " + error.getMessage()));
  }

  /**
   * Méthode pour appeler l'API avec une requête PUT
   * 
   * @param routeName Nom de la route à appeler.
   * @param body     Corps de la requête (peut être null pour une requête sans corps).
   * @return la réponse de l'API encapsulée.
   */
  public Mono<ApiFactory> callAPIPut(String routeName, Map<String, Object> body) {
    logger.INFO("Appel PUT vers: " + routeName);
    return webClient.put()
        .uri(routeName)
        .bodyValue(body != null ? body : Map.of())
        .retrieve()
            .bodyToMono(String.class)
            .subscribeOn(Schedulers.boundedElastic())
            .map(rawJson -> {
              response.parseFromRawJson(rawJson);
              return response;
            })
            .doOnNext(lastResponse::set)
        .doOnError(error -> logger.CRITICAL("Erreur lors de l'appel PUT: " + error.getMessage()));
  }

  /**
   * Méthode pour appeler l'API avec une requête PATCH
   * 
   * @param routeName Nom de la route à appeler.
   * @param body    Corps de la requête (peut être null pour une requête sans corps).
   * @return la réponse de l'API encapsulée.
   */
  public Mono<ApiFactory> callAPIPatch(String routeName, Map<String, Object> body) {
    logger.INFO("Appel PATCH vers: " + routeName);
    return webClient.patch()
        .uri(routeName)
        .bodyValue(body != null ? body : Map.of())
        .retrieve()
            .bodyToMono(String.class)
            .subscribeOn(Schedulers.boundedElastic())
            .map(rawJson -> {
              response.parseFromRawJson(rawJson);
              return response;
            })
            .doOnNext(lastResponse::set)
        .doOnError(error -> logger.CRITICAL("Erreur lors de l'appel PATCH: " + error.getMessage()));
  }

  /**
   * Méthode pour appeler l'API avec une requête DELETE
   * 
   * @param routeName Nom de la route à appeler.
   * @return la réponse de l'API encapsulée.
   */
  public Mono<ApiFactory> callAPIDelete(String routeName) {
    logger.INFO("Appel DELETE vers: " + routeName);
    return webClient.delete()
        .uri(routeName)
        .retrieve()
            .bodyToMono(String.class)
            .subscribeOn(Schedulers.boundedElastic())
            .map(rawJson -> {
              response.parseFromRawJson(rawJson);
              return response;
            })
            .doOnNext(lastResponse::set)
        .doOnError(error -> logger.CRITICAL("Erreur lors de l'appel PATCH: " + error.getMessage()));
  }
}
