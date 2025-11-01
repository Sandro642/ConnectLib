package fr.sandro642.github.api;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.enums.lang.CategoriesType;
import fr.sandro642.github.spring.controller.DataController;
import fr.sandro642.github.spring.dto.Request;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ApiClient is a class that provides methods to interact with the ConnectLib API.
 * It extends ApiFactory and uses WebClient to make HTTP requests.
 * This class supports various HTTP methods including GET, POST, PUT, PATCH, and DELETE.
 * It handles the API responses and errors, logging them appropriately.
 *
 * @author Sandro642
 * @version 1.0
 */

public class ApiClient extends ApiFactory {

    /**
     * connectLib is an instance of ConnectLib that provides access to the library's configuration and utilities.
     */
    private ConnectLib connectLib = new ConnectLib();

    /**
     * WebClient is a non-blocking, reactive HTTP client for making requests to the API.
     * It is initialized with the base URL from the ConnectLib configuration.
     */
    private final WebClient webClient;

    /**
     * lastResponse is an AtomicReference that holds the last response received from the API.
     * It is used to store the response for later retrieval or processing.
     */
    private final AtomicReference<ApiFactory> lastResponse = new AtomicReference<>();

    /**
     * response is an instance of ApiFactory that is used to parse and store the raw JSON response from the API.
     * It provides methods to handle the response data.
     */
    private final ApiFactory apiFactory = new ApiFactory();

    /**
     * baseUrl is a static string that holds the base URL for the API.
     * It is initialized in the constructor and used for making requests.
     */
    private static String baseUrl;

    /**
     * Constructor for ApiClient.
     * It initializes the WebClient with the base URL from the ConnectLib configuration.
     * If the base URL is not found, it throws a RuntimeException.
     */
    public ApiClient(String baseUrlLambda) {
        baseUrl = baseUrlLambda;

        if (baseUrl == null) {
            connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "construct.urlbase"));
        }

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    /**
     * Method to call the API with a GET request.
     * @param routeName Name of the route to call.
     * @return a Mono that emits the ApiFactory response containing the parsed JSON data.
     */
    public Mono<ApiFactory> callAPIGet(String routeName) {
        connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "call.get", Map.of("routename", routeName)));

        Request r = DataController.getInstance().createRequest(routeName, baseUrl);

        record ResponseData(int statusCode, String body) {}

        return webClient.get()
                .uri(routeName)
                .exchangeToMono(response ->
                        response.bodyToMono(String.class)
                                .map(rawJson -> new ResponseData(response.statusCode().value(), rawJson))
                )
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(responseData -> {
                    connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "call.threadinuse", "thread", Thread.currentThread().getName()));

                    String newStatus = (responseData.statusCode() >= 200 && responseData.statusCode() < 300) ? "success" : "error";
                    try {
                        DataController.getInstance().updateRequestStatus(r.getId(), newStatus);
                    } catch (Exception e) {
                        connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "general.error", Map.of("method", "GET", "exception", e.getMessage())));
                    }
                })
                .map(responseData -> {
                    apiFactory.setStatusCode(responseData.statusCode());
                    apiFactory.parseFromRawJson(responseData.body);
                    return apiFactory;
                })
                .doOnNext(lastResponse::set)
                .doOnError(error -> connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "general.error", Map.of("method", "GET", "exception", error.getMessage()))));
    }

    /**
     * Method to call the API with a POST request.
     * @param routeName Name of the route to call.
     * @param body   Body of the request (can be null for a request without body).
     * @return a Mono that emits the ApiFactory response containing the parsed JSON data.
     */
    public Mono<ApiFactory> callAPIPost(String routeName, Map<String, Object> body) {
        connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "call.post", Map.of("routename", routeName)));

        Request r = DataController.getInstance().createRequest(routeName, baseUrl);

        record ResponseData(int statusCode, String body) {}

        return webClient.post()
                .uri(routeName)
                .bodyValue(body != null ? body : Map.of())
                .exchangeToMono(response ->
                        response.bodyToMono(String.class)
                                .map(rawJson -> new ResponseData(response.statusCode().value(), rawJson))
                )
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(responseData -> {
                    connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "call.threadinuse", "thread", Thread.currentThread().getName()));

                    String newStatus = (responseData.statusCode() >= 200 && responseData.statusCode() < 300) ? "success" : "error";
                    try {
                        DataController.getInstance().updateRequestStatus(r.getId(), newStatus);
                    } catch (Exception e) {
                        connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "general.error", Map.of("method", "GET", "exception", e.getMessage())));
                    }
                })
                .map(responseData -> {
                    apiFactory.setStatusCode(responseData.statusCode());
                    apiFactory.parseFromRawJson(responseData.body());
                    return apiFactory;
                })
                .doOnNext(lastResponse::set)
                .doOnError(error -> connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "general.error", Map.of("method", "POST", "exception", error.getMessage()))));
    }

    /**
     * Method to call the API with a PUT request.
     * @param routeName Name of the route to call.
     * @param body  Body of the request (can be null for a request without body).
     * @return a Mono that emits the ApiFactory response containing the parsed JSON data.
     */
    public Mono<ApiFactory> callAPIPut(String routeName, Map<String, Object> body) {
        connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "call.put", Map.of("routename", routeName)));

        Request r = DataController.getInstance().createRequest(routeName, baseUrl);

        record ResponseData(int statusCode, String body) {}

        return webClient.put()
                .uri(routeName)
                .bodyValue(body != null ? body : Map.of())
                .exchangeToMono(response ->
                        response.bodyToMono(String.class)
                                .map(rawJson -> new ResponseData(response.statusCode().value(), rawJson))
                )
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(responseData -> {
                    connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "call.threadinuse", "thread", Thread.currentThread().getName()));

                    String newStatus = (responseData.statusCode() >= 200 && responseData.statusCode() < 300) ? "success" : "error";
                    try {
                        DataController.getInstance().updateRequestStatus(r.getId(), newStatus);
                    } catch (Exception e) {
                        connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "general.error", Map.of("method", "GET", "exception", e.getMessage())));
                    }
                })
                .map(responseData -> {
                    apiFactory.setStatusCode(responseData.statusCode());
                    apiFactory.parseFromRawJson(responseData.body());
                    return apiFactory;
                })
                .doOnNext(lastResponse::set)
                .doOnError(error -> connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "general.error", Map.of("method", "PUT", "exception", error.getMessage()))));
    }

    /**
     * Method to call the API with a PATCH request.
     * @param routeName Name of the route to call.
     * @param body Body of the request (can be null for a request without body).
     * @return a Mono that emits the ApiFactory response containing the parsed JSON data.
     */
    public Mono<ApiFactory> callAPIPatch(String routeName, Map<String, Object> body) {
        connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "call.patch", Map.of("routename", routeName)));

        Request r = DataController.getInstance().createRequest(routeName, baseUrl);

        record ResponseData(int statusCode, String body) {}

        return webClient.patch()
                .uri(routeName)
                .bodyValue(body != null ? body : Map.of())
                .exchangeToMono(response ->
                        response.bodyToMono(String.class)
                                .map(rawJson -> new ResponseData(response.statusCode().value(), rawJson))
                )
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(responseData -> {
                    connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "call.threadinuse", "thread", Thread.currentThread().getName()));

                    String newStatus = (responseData.statusCode() >= 200 && responseData.statusCode() < 300) ? "success" : "error";
                    try {
                        DataController.getInstance().updateRequestStatus(r.getId(), newStatus);
                    } catch (Exception e) {
                        connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "general.error", Map.of("method", "GET", "exception", e.getMessage())));
                    }
                })
                .map(responseData -> {
                    apiFactory.setStatusCode(responseData.statusCode());
                    apiFactory.parseFromRawJson(responseData.body());
                    return apiFactory;
                })
                .doOnNext(lastResponse::set)
                .doOnError(error -> connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "general.error", Map.of("method", "PATCH", "exception", error.getMessage()))));
    }

    /**
     * Method to call the API with a DELETE request.
     * @param routeName Name of the route to call.
     * @return a Mono that emits the ApiFactory response containing the parsed JSON data.
     */
    public Mono<ApiFactory> callAPIDelete(String routeName) {
        connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "call.delete", Map.of("routename", routeName)));

        Request r = DataController.getInstance().createRequest(routeName, baseUrl);

        record ResponseData(int statusCode, String body) {}

        return webClient.delete()
                .uri(routeName)
                .exchangeToMono(response ->
                        response.bodyToMono(String.class)
                                .map(rawJson -> new ResponseData(response.statusCode().value(), rawJson))
                )
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(responseData -> {
                    connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "call.threadinuse", "thread", Thread.currentThread().getName()));

                    String newStatus = (responseData.statusCode() >= 200 && responseData.statusCode() < 300) ? "success" : "error";
                    try {
                        DataController.getInstance().updateRequestStatus(r.getId(), newStatus);
                    } catch (Exception e) {
                        connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "general.error", Map.of("method", "GET", "exception", e.getMessage())));
                    }
                })
                .map(responseData -> {
                    apiFactory.setStatusCode(responseData.statusCode());
                    apiFactory.parseFromRawJson(responseData.body());
                    return apiFactory;
                })
                .doOnNext(lastResponse::set)
                .doOnError(error -> connectLib.Logger().CRITICAL(connectLib.LangManager().getMessage(CategoriesType.APICLIENT_CLASS, "general.error", Map.of("method", "DELETE", "exception", error.getMessage()))));
    }
}
