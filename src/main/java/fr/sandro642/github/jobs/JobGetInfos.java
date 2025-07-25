package fr.sandro642.github.jobs;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.api.ApiClient;
import fr.sandro642.github.api.ApiFactory;
import fr.sandro642.github.enums.MethodType;
import fr.sandro642.github.enums.VersionType;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * JobGetInfos is a utility class for managing API requests in the ConnectLib library.
 * It provides methods to construct API routes based on the configuration defined in a YAML file.
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class JobGetInfos {

    /**
     * ApiClient is used to make API calls.
     * It is initialized in the constructor of JobGetInfos.
     */
    private final ApiClient apiClient;

    /**
     * Constructor of JobGetInfos.
     * Initializes the ApiClient and loads the YAML configuration.
     */
    public JobGetInfos() {
        this.apiClient = new ApiClient();
        ConnectLib.YamlUtils();
    }

    /**
     * Converts the route name to lowercase.
     * This method is used to ensure that the route names match the keys in the YAML configuration.
     * @param routeName The enum representing the route name.
     * @return The lowercase string representation of the route name.
     */
    private String getRouteName(Enum<?> routeName) {
        return routeName.name().toLowerCase();
    }

    /**
     * Get routes from the YAML file and builds the full URL.
     * @param versionType Version of the API (V1_BRANCH, V2_BRANCH)
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName Name of the route in the YAML file
     * @param body Body of the request for POST (can be null for GET)
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(VersionType versionType, MethodType methodType, Enum<?> routeName, Map<String, ?> body) {
        return getRoutes(versionType, methodType, getRouteName(routeName), body, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with parameters.
     * @param versionType Version of the API (V1_BRANCH, V2_BRANCH)
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName Name of the route in the YAML file
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(VersionType versionType, MethodType methodType, Enum<?> routeName) {
        return getRoutes(versionType, methodType, getRouteName(routeName), null, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with a request body.
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName Name of the route in the YAML file
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, Enum<?> routeName) {
        return getRoutes(null, methodType, getRouteName(routeName), null, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with a request body and parameters.
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName Name of the route in the YAML file
     * @param body Body of the request for POST (can be null for GET)
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, Enum<?> routeName, Map<String, ?> body) {
        return getRoutes(null, methodType, getRouteName(routeName), body, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with additional parameters.
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName Name of the route in the YAML file
     * @param params Additional parameters for the request
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, Enum<?> routeName, Map<String, ?> body, Map<String, ?> params) {
        return getRoutes(null, methodType, getRouteName(routeName), body, params);
    }

    /**
     * Get routes from the YAML file and builds the full URL.
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName Name of the route in the YAML file
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, String routeName) {
        return getRoutes(null, methodType, routeName, null, null);
    }

    /**
     * Récupère les routes depuis le fichier YAML et construit l'URL complète
     * @param versionType Version de l'API (V1_BRANCH, V2_BRANCH)
     * @param methodType Type de méthode HTTP (GET, POST)
     * @param routeName Nom de la route dans le fichier YAML
     * @return JobGetInfos pour chaînage
     */
    public JobGetInfos getRoutes(VersionType versionType, MethodType methodType, String routeName) {
        return getRoutes(versionType, methodType, routeName, null, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with a request body.
     * @param versionType Version of the API (V1_BRANCH, V2_BRANCH)
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName Name of the route in the YAML file
     * @param body Body of the request for POST (can be null for GET)
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(VersionType versionType, MethodType methodType, String routeName, Map<String, ?> body) {
        return getRoutes(versionType, methodType, routeName, body, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with additional parameters.
     * @param versionType Version of the API (V1_BRANCH, V2_BRANCH)
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName Name of the route in the YAML file
     * @param params Additional parameters for the request
     * @return JobGetInfos for chaining
     */
    public <R> JobGetInfos getRoutes(VersionType versionType, MethodType methodType, R routeName, Map<String, ?> body, Map<String, ?> params) {
        try {
            String route = ConnectLib.getRoute(routeName.toString().toLowerCase());

            String fullRoute;

            if (versionType != null && versionType.getVersion() != null) {
                fullRoute = "/" + versionType.getVersion() + route;
            } else {
                fullRoute = route;
            }

            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, ?> entry : params.entrySet()) {
                    String paramKey = "{" + entry.getKey() + "}";
                    String paramValue = entry.getValue().toString();

                    fullRoute = fullRoute.replace(paramKey, paramValue);
                }
            }

            ConnectLib.StoreAndRetrieve().store.put("currentRoute", fullRoute);
            ConnectLib.StoreAndRetrieve().store.put("currentMethod", methodType);

            if (body != null) {
                ConnectLib.StoreAndRetrieve().store.put("currentBody", body);
            }

            if (params != null) {
                ConnectLib.StoreAndRetrieve().store.put("currentParams", params);
            }

            ConnectLib.Logger().INFO("Route construite: " + fullRoute);

        } catch (Exception e) {
            ConnectLib.Logger().ERROR("Error while constructing the route : " + e.getMessage());
        }

        return this;
    }

    /**
     * Get the response from the API based on the current route and method.
     * This method retrieves the stored route, method, and body from the store,
     * makes the API call, and returns the response as an ApiFactory object.
     * @return ApiFactory containing the response from the API, or null if an error occurs.
     */
    public CompletableFuture<ApiFactory> getResponse() {
        try {
            String route = (String) ConnectLib.StoreAndRetrieve().store.get("currentRoute");
            MethodType method = (MethodType) ConnectLib.StoreAndRetrieve().store.get("currentMethod");
            Map<String, Object> body = (Map<String, Object>) ConnectLib.StoreAndRetrieve().store.get("currentBody");

            if (route == null || method == null) {
                return CompletableFuture.failedFuture(
                        new RuntimeException("Route or method not set. Please call getRoutes() first.")
                );
            }

            CompletableFuture<ApiFactory> responseFuture = new CompletableFuture<>();

            // Callback to clean up the store after the response
            Consumer<ApiFactory> onSuccess = response -> {
                ConnectLib.StoreAndRetrieve().store.remove("currentRoute");
                ConnectLib.StoreAndRetrieve().store.remove("currentMethod");
                ConnectLib.StoreAndRetrieve().store.remove("currentBody");
                responseFuture.complete(response);
            };

            Consumer<Throwable> onError = error -> {
                ConnectLib.StoreAndRetrieve().store.remove("currentRoute");
                ConnectLib.StoreAndRetrieve().store.remove("currentMethod");
                ConnectLib.StoreAndRetrieve().store.remove("currentBody");
                responseFuture.completeExceptionally(error);
            };

            switch(method) {
                case GET:
                    apiClient.callAPIGet(route).subscribe(
                            response -> onSuccess.accept((ApiFactory) response),
                            onError
                    );
                    break;
                case POST:
                    apiClient.callAPIPost(route, body).subscribe(
                            response -> onSuccess.accept((ApiFactory) response),
                            onError
                    );
                    break;
                case PUT:
                    apiClient.callAPIPut(route, body).subscribe(
                            response -> onSuccess.accept((ApiFactory) response),
                            onError
                    );
                    break;
                case PATCH:
                    apiClient.callAPIPatch(route, body).subscribe(
                            response -> onSuccess.accept((ApiFactory) response),
                            onError
                    );
                    break;
                case DELETE:
                    apiClient.callAPIDelete(route).subscribe(
                            response -> onSuccess.accept((ApiFactory) response),
                            onError
                    );
                    break;
                default:
                    ConnectLib.Logger().ERROR("Unsupported method type: " + method);
                    return CompletableFuture.failedFuture(
                            new IllegalArgumentException("Unsupported method type: " + method)
                    );
            }

            return responseFuture;

        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
