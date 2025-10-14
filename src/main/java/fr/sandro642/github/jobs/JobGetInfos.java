package fr.sandro642.github.jobs;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.api.ApiClient;
import fr.sandro642.github.api.ApiFactory;
import fr.sandro642.github.enums.MethodType;
import fr.sandro642.github.enums.lang.CategoriesType;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JobGetInfos is a utility class for managing API requests in the connectLib library.
 * It provides methods to construct API routes based on the configuration defined in a YAML file.
 *
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class JobGetInfos {

    /**
     * ApiClient is used to make API calls.
     * It is initialized in the constructor of JobGetInfos.
     */
    private ApiClient apiClient;

    /**
     * connectLib instance to access its methods and properties.
     */
    private final ConnectLib connectLib = new ConnectLib();

    /**
     * URLProvider instance to provide custom URL branches.
     * If not set, the default URL from the configuration will be used.
     */
    private URLProvider urlBranch;

    /**
     * Constructor of JobGetInfos.
     * Initializes the ApiClient and loads the YAML configuration.
     */
    public JobGetInfos() {
        connectLib.YamlUtils();
    }

    /**
     * Converts the route name to lowercase.
     * This method is used to ensure that the route names match the keys in the YAML configuration.
     *
     * @param routeName The enum representing the route name.
     * @return The lowercase string representation of the route name.
     */
    private String getRouteName(Enum<?> routeName) {
        return routeName.name().toLowerCase();
    }

    /**
     * Get routes from the YAML file and builds the full URL.
     *
     * @param versionType Version of the API (V1_BRANCH, V2_BRANCH)
     * @param methodType  Type of HTTP method (GET, POST)
     * @param routeName   Name of the route in the YAML file
     * @param body        Body of the request for POST (can be null for GET)
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(VersionProvider versionType, MethodType methodType, Enum<?> routeName, Map<String, ?> body) {
        return getRoutes(versionType, methodType, getRouteName(routeName), body, null, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with parameters.
     *
     * @param versionType Version of the API (V1_BRANCH, V2_BRANCH)
     * @param methodType  Type of HTTP method (GET, POST)
     * @param routeName   Name of the route in the YAML file
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(VersionProvider versionType, MethodType methodType, Enum<?> routeName) {
        return getRoutes(versionType, methodType, getRouteName(routeName), null, null, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with a request body.
     *
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName  Name of the route in the YAML file
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, Enum<?> routeName) {
        return getRoutes(null, methodType, getRouteName(routeName), null, null, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with a request body and parameters.
     *
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName  Name of the route in the YAML file
     * @param body       Body of the request for POST (can be null for GET)
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, Enum<?> routeName, Map<String, ?> body) {
        return getRoutes(null, methodType, getRouteName(routeName), body, null, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with additional parameters.
     *
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName  Name of the route in the YAML file
     * @param params     Additional parameters for the request
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, Enum<?> routeName, Map<String, ?> body, Map<String, ?> params) {
        return getRoutes(null, methodType, getRouteName(routeName), body, params, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL.
     *
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName  Name of the route in the YAML file
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, String routeName) {
        return getRoutes(null, methodType, routeName, null, null, null);
    }

    /**
     * Récupère les routes depuis le fichier YAML et construit l'URL complète
     *
     * @param versionType Version de l'API (V1_BRANCH, V2_BRANCH)
     * @param methodType  Type de méthode HTTP (GET, POST)
     * @param routeName   Nom de la route dans le fichier YAML
     * @return JobGetInfos pour chaînage
     */
    public JobGetInfos getRoutes(VersionProvider versionType, MethodType methodType, String routeName) {
        return getRoutes(versionType, methodType, routeName, null, null, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with a request body.
     *
     * @param versionType Version of the API (V1_BRANCH, V2_BRANCH)
     * @param methodType  Type of HTTP method (GET, POST)
     * @param routeName   Name of the route in the YAML file
     * @param body        Body of the request for POST (can be null for GET)
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(VersionProvider versionType, MethodType methodType, String routeName, Map<String, ?> body) {
        return getRoutes(versionType, methodType, routeName, body, null, null);
    }

    /**
     * Get routes from the YAML file and builds the full URL with a request body and parameters.
     *
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName  Name of the route in the YAML file
     * @param body       Body of the request for POST (can be null for GET)
     * @param params     Additional parameters for the request
     * @param query      Additional query parameters for the request
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, String routeName, Map<String, ?> body, Map<String, ?> params, Map<String, ?> query) {
        return getRoutes(null, methodType, routeName, body, params, query);
    }

    /**
     * Get routes from the YAML file and builds the full URL with additional parameters.
     *
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName  Name of the route in the YAML file
     * @param body       Body of the request for POST (can be null for GET)
     * @param params     Additional parameters for the request
     * @param query      Additional query parameters for the request
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, Enum<?> routeName, Map<String, ?> body, Map<String, ?> params, Map<String, ?> query) {
        return getRoutes(null, methodType, getRouteName(routeName), body, params, query);
    }

    /**
     * Get routes from the YAML file and builds the full URL with additional parameters.
     *
     * @param versionType Version of the API (V1_BRANCH, V2_BRANCH)
     * @param methodType  Type of HTTP method (GET, POST)
     * @param routeName   Name of the route in the YAML file
     * @param params      Additional parameters for the request
     * @param query       Additional query parameters for the request
     * @return JobGetInfos for chaining
     */
    public <R> JobGetInfos getRoutes(VersionProvider versionType, MethodType methodType, R routeName, Map<String, ?> body, Map<String, ?> params, Map<String, ?> query) {
        try {
            String route = connectLib.getRoute(routeName.toString().toLowerCase());

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

            // New query string handling logic
            if (query != null && !query.isEmpty()) {
                // Pattern for capturing parameters between $
                Pattern pattern = Pattern.compile("\\$([^$]+)\\$");
                Matcher matcher = pattern.matcher(fullRoute);

                StringBuilder queryString = new StringBuilder("?");
                boolean firstParam = true;

                // Iterate through all matches (parameters between $)
                while (matcher.find()) {
                    String paramName = matcher.group(1); // Get parameter name without $

                    // Check if this parameter exists in the query map
                    if (query.containsKey(paramName)) {
                        if (!firstParam) {
                            queryString.append("&");
                        }
                        queryString.append(paramName).append("=").append(query.get(paramName));
                        firstParam = false;
                    }
                }

                // Append query string to the route only if parameters were added
                if (queryString.length() > 1) {
                    // Clean the route by removing $parameter$ placeholders and extra &
                    String cleanRoute = fullRoute.replaceAll("\\$[^$]+\\$", "").replaceAll("&+", "");
                    fullRoute = cleanRoute + queryString;
                }
            }

            connectLib.StoreAndRetrieve().store.put("currentRoute", fullRoute);
            connectLib.StoreAndRetrieve().store.put("currentMethod", methodType);

            if (body != null) {
                connectLib.StoreAndRetrieve().store.put("currentBody", body);
            }

            if (params != null) {
                connectLib.StoreAndRetrieve().store.put("currentParams", params);
            }

            connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.JOBS_PACKAGE, "getroutes.maderoute", "route", fullRoute));
        } catch (Exception e) {
            connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.JOBS_PACKAGE, "getroutes.error", "exception", e.getMessage()));
        }
        return this;
    }

    /**
     * Set a custom URL branch for the API calls.
     * If not set, the default URL from the configuration will be used.
     *
     * @param urlBranch The URLProvider instance providing the custom URL branch.
     * @return JobGetInfos for chaining
     */
    public JobGetInfos urlBranch(URLProvider urlBranch) {

        this.urlBranch = urlBranch;
        return this;
    }

    /**
     * Get the response from the API based on the current route and method.
     * This method retrieves the stored route, method, and body from the store,
     * makes the API call, and returns the response as an ApiFactory object.
     *
     * @return ApiFactory containing the response from the API, or null if an error occurs.
     */
    public CompletableFuture<ApiFactory> getResponse() {
        try {
            String route = (String) connectLib.StoreAndRetrieve().store.get("currentRoute");
            MethodType method = (MethodType) connectLib.StoreAndRetrieve().store.get("currentMethod");
            Map<String, Object> body = (Map<String, Object>) connectLib.StoreAndRetrieve().store.get("currentBody");

            if (route == null || method == null) {
                connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.JOBS_PACKAGE, "getresponse.mustbe"));
            }

            /** Determine the URL branch to use */
            String urlBranchLambda;

            if (urlBranch == null) {
                urlBranchLambda = (String) connectLib.StoreAndRetrieve().store.get(connectLib.StoreAndRetrieve().URL_KEY);
            } else {
                urlBranchLambda = urlBranch.getURL();
            }
            apiClient = new ApiClient(urlBranchLambda);

            CompletableFuture<ApiFactory> responseFuture = new CompletableFuture<>();

            // Callback to clean up the store after the response
            Consumer<ApiFactory> onSuccess = response -> {
                connectLib.StoreAndRetrieve().store.remove("currentRoute");
                connectLib.StoreAndRetrieve().store.remove("currentMethod");
                connectLib.StoreAndRetrieve().store.remove("currentBody");
                responseFuture.complete(response);
            };

            Consumer<Throwable> onError = error -> {
                connectLib.StoreAndRetrieve().store.remove("currentRoute");
                connectLib.StoreAndRetrieve().store.remove("currentMethod");
                connectLib.StoreAndRetrieve().store.remove("currentBody");
                responseFuture.completeExceptionally(error);
            };

            switch (method) {
                case GET:
                    apiClient.callAPIGet(route).subscribe(
                            response -> onSuccess.accept(response),
                            onError
                    );
                    break;
                case POST:
                    apiClient.callAPIPost(route, body).subscribe(
                            response -> onSuccess.accept(response),
                            onError
                    );
                    break;
                case PUT:
                    apiClient.callAPIPut(route, body).subscribe(
                            response -> onSuccess.accept(response),
                            onError
                    );
                    break;
                case PATCH:
                    apiClient.callAPIPatch(route, body).subscribe(
                            response -> onSuccess.accept(response),
                            onError
                    );
                    break;
                case DELETE:
                    apiClient.callAPIDelete(route).subscribe(
                            response -> onSuccess.accept(response),
                            onError
                    );
                    break;
                default:
                    connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.JOBS_PACKAGE, "getresponse.error", "type", method.toString()));
                    System.exit(2);
            }

            return responseFuture;

        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}
