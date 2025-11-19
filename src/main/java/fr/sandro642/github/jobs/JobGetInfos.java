package fr.sandro642.github.jobs;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.api.ApiClient;
import fr.sandro642.github.api.ApiFactory;
import fr.sandro642.github.enums.MethodType;
import fr.sandro642.github.enums.lang.CategoriesType;
import fr.sandro642.github.provider.URLProvider;
import fr.sandro642.github.provider.VersionProvider;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JobGetInfos is a utility class for managing API requests in the connectLib library.
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
    private ApiClient apiClient;

    /**
     * connectLib instance to access its methods and properties.
     */
    private ConnectLib connectLib = new ConnectLib();

    /**
     * URLProvider instance to provide custom URL branches.
     * If not set, the default URL from the configuration will be used.
     */
    private URLProvider urlBranch;

    private String fullRoute = "";

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
     * @param routeName The enum representing the route name.
     * @return The lowercase string representation of the route name.
     */
    private String getRouteName(Enum<?> routeName) {
        return routeName.name().toLowerCase();
    }

    /**
     * Get routes from the YAML file and builds the full URL with a request body.
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName Name of the route in the YAML file
     * @return JobGetInfos for chaining
     */
    public JobGetInfos getRoutes(MethodType methodType, Enum<?> routeName) {
        return getRoutes(null, methodType, getRouteName(routeName));
    }

    /**
     * Get routes from the YAML file and builds the full URL with a request body.
     * @param versionType VersionProvider to specify API versioning
     * @param methodType Type of HTTP method (GET, POST)
     * @param routeName Name of the route in the YAML file
     * @return JobGetInfos for chaining
     */
    public <R> JobGetInfos getRoutes(VersionProvider versionType, MethodType methodType, R routeName) {
        try {
            String route = connectLib.getRoute(routeName.toString().toLowerCase());

            if (versionType != null && versionType.getVersion() != null) {
                String cleanRoute = route.startsWith("/") ? route.substring(1) : route;
                fullRoute = "/" + versionType.getVersion() + "/" + cleanRoute;
            } else {
                this.fullRoute = route;
            }

            connectLib.StoreAndRetrieve().store.put("currentRoute", this.fullRoute);
            connectLib.StoreAndRetrieve().store.put("currentMethod", methodType);

            connectLib.Logger().INFO(connectLib.LangManager().getMessage(CategoriesType.JOBS_PACKAGE, "getroutes.maderoute", "route", fullRoute));
        } catch (Exception e) {
            connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.JOBS_PACKAGE, "getroutes.error", "exception", e.getMessage()));
        }
        return this;
    }

    public JobGetInfos body(Map<?, ?> body) {
        connectLib.StoreAndRetrieve().store.put("currentBody", body);
        return this;
    }

    public JobGetInfos params(Map<?, ?> params) {
        if (params == null || params.isEmpty()) return this;

        for (Map.Entry<?, ?> entry : params.entrySet()) {
            String paramKey = "{" + entry.getKey() + "}";
            String paramValue = entry.getValue() == null ? "" : entry.getValue().toString();
            this.fullRoute = this.fullRoute.replace(paramKey, paramValue);
        }

        connectLib.StoreAndRetrieve().store.put("currentParams", params);
        connectLib.StoreAndRetrieve().store.put("currentRoute", this.fullRoute);
        return this;
    }

    public JobGetInfos query(Map<?, ?> query) {
        if (query == null || query.isEmpty()) return this;

        Pattern pattern = Pattern.compile("\\$([^$]+)\\$");
        Matcher matcher = pattern.matcher(this.fullRoute);

        StringBuilder queryString = new StringBuilder("?");
        boolean firstParam = true;

        while (matcher.find()) {
            String paramName = matcher.group(1);
            if (query.containsKey(paramName)) {
                if (!firstParam) {
                    queryString.append("&");
                }
                Object value = query.get(paramName);
                queryString.append(paramName).append("=").append(value == null ? "" : value.toString());
                firstParam = false;
            }
        }

        if (queryString.length() > 1) {
            String cleanRoute = this.fullRoute.replaceAll("\\$[^$]+\\$", "").replaceAll("&+", "");
            this.fullRoute = cleanRoute + queryString.toString();
        }

        connectLib.StoreAndRetrieve().store.put("currentRoute", this.fullRoute);
        connectLib.StoreAndRetrieve().store.put("currentQuery", query);
        return this;
    }

    /**
     * Set a custom URL branch for the API calls.
     * If not set, the default URL from the configuration will be used.
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
     * @return ApiFactory containing the response from the API, or null if an error occurs.
     */
    public CompletableFuture<ApiFactory> execute() {
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

            switch(method) {
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
