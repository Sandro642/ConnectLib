package fr.sandro642.github.jobs;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.api.ApiClient;
import fr.sandro642.github.api.ApiResponse;
import fr.sandro642.github.jobs.misc.MethodType;
import fr.sandro642.github.jobs.misc.VersionType;
import fr.sandro642.github.utils.YamlUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * JobGetInfos est une classe qui permet de récupérer des informations depuis une API en utilisant des routes définies dans un fichier YAML.
 * Elle utilise ApiClient pour effectuer les requêtes HTTP et YamlUtils pour lire les routes depuis le fichier YAML.
 *
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class JobGetInfos {

    /**
     * ApiClient est utilisé pour effectuer les requêtes HTTP vers l'API.
     * YamlUtils est utilisé pour lire les routes depuis le fichier YAML.
     */
    private final ApiClient apiClient;
    private final YamlUtils yamlUtils;

    /**
     * Constructeur de JobGetInfos qui initialise ApiClient et YamlUtils.
     * Utilise ResourceType.MAIN_RESOURCES pour le type de ressource par défaut.
     */
    public JobGetInfos() {
        this.apiClient = new ApiClient();
        this.yamlUtils = ConnectorAPI.YamlUtils();
    }

    /**
     * Récupère les routes depuis le fichier YAML et construit l'URL complète
     * @param methodType Type de méthode HTTP (GET, POST)
     * @param routeName Nom de la route dans le fichier YAML
     * @return JobGetInfos pour chaînage
     */
    public JobGetInfos getRoutes(MethodType methodType, String routeName) {
        return getRoutes(null, methodType, routeName, null, null);
    }

    /**
     * Récupère les routes depuis le fichier YAML et construit l'URL complète avec un corps de requête
     * @param methodType Type de méthode HTTP (GET, POST)
     * @param routeName Nom de la route dans le fichier YAML
     * @param body Corps de la requête pour POST (peut être null pour GET)
     * @return JobGetInfos pour chaînage
     */
    public JobGetInfos getRoutesWithBody(MethodType methodType, String routeName, Map<String, Object> body) {
        return getRoutes(null, methodType, routeName, body, null);
    }

    /**
     * Récupère les routes depuis le fichier YAML et construit l'URL complète avec des paramètres
     * @param methodType Type de méthode HTTP (GET, POST)
     * @param routeName Nom de la route dans le fichier YAML
     * @param params Paramètres supplémentaires pour la requête
     * @return JobGetInfos pour chaînage
     */
    public JobGetInfos getRoutesWithParams(MethodType methodType, String routeName, Map<String, Object> params) {
        return getRoutes(null, methodType, routeName, null, params);
    }

    /**
     * Récupère les routes depuis le fichier YAML et construit l'URL complète avec un corps de requête et des paramètres
     * @param methodType Type de méthode HTTP (GET, POST)
     * @param routeName Nom de la route dans le fichier YAML
     * @param body Corps de la requête pour POST (peut être null pour GET)
     * @param params Paramètres supplémentaires pour la requête
     * @return JobGetInfos pour chaînage
     */
    public JobGetInfos getRoutesBoth(MethodType methodType, String routeName, Map<String, Object> body, Map<String, Object> params) {
        return getRoutes(null, methodType, routeName, body, params);
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
     * Récupère les routes depuis le fichier YAML et construit l'URL complète
     * @param versionType Version de l'API (V1_BRANCH, V2_BRANCH)
     * @param methodType Type de méthode HTTP (GET, POST)
     * @param routeName Nom de la route dans le fichier YAML
     * @param body Corps de la requête pour POST (peut être null pour GET)
     * @return JobGetInfos pour chaînage
     */
    public JobGetInfos getRoutes(VersionType versionType, MethodType methodType, String routeName, Map<String, Object> body) {
        return getRoutes(versionType, methodType, routeName, body, null);
    }

    /**
     * Récupère les routes depuis le fichier YAML et construit l'URL complète
     * @param versionType Version de l'API (V1_BRANCH, V2_BRANCH)
     * @param methodType Type de méthode HTTP (GET, POST)
     * @param routeName Nom de la route dans le fichier YAML
     * @param body Corps de la requête pour POST (peut être null pour GET)
     * @param params Paramètres supplémentaires pour la requête (optionnel)
     * @return JobGetInfos pour chaînage
     */
    public JobGetInfos getRoutes(VersionType versionType, MethodType methodType, String routeName, Map<String, Object> body, Map<String, Object> params) {
        try {
            // Récupère la route depuis le fichier YAML
            String route = yamlUtils.getRoute(routeName);

            String fullRoute;

            // Vérification de null pour éviter NullPointerException
            if (versionType != null && versionType.getVersion() != null) {
                // Construit l'URL complète avec la version
                fullRoute = "/" + versionType.getVersion() + route;
            } else {
                // Construit l'URL complète sans version
                fullRoute = route;
            }

            // Remplace les paramètres dans la route si présents
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String paramKey = "{" + entry.getKey() + "}";
                    String paramValue = entry.getValue().toString();

                    // Remplace toutes les occurrences du paramètre dans la route
                    fullRoute = fullRoute.replace(paramKey, paramValue);
                }
            }

            // Stocke les informations pour la requête
            ConnectorAPI.StoreAndRetrieve().store.put("currentRoute", fullRoute);
            ConnectorAPI.StoreAndRetrieve().store.put("currentMethod", methodType);

            if (body != null) {
                ConnectorAPI.StoreAndRetrieve().store.put("currentBody", body);
            }

            // Stocke les paramètres si présents
            if (params != null) {
                ConnectorAPI.StoreAndRetrieve().store.put("currentParams", params);
            }

            //ConnectorAPI.HookManager().saveData(ConnectorAPI.StoreAndRetrieve().store, "store_and_retrieve.yml");

            ConnectorAPI.Logger().INFO("Route construite: " + fullRoute);

        } catch (Exception e) {
            ConnectorAPI.Logger().ERROR("Erreur lors de la construction de la route: " + e.getMessage());
            throw new RuntimeException("Erreur lors de la construction de la route", e);
        }

        return this;
    }

    /**
     * Exécute la requête API et retourne la réponse
     * @return ApiResponse contenant la réponse de l'API
     */
    public ApiResponse<Void> getResponse() {
        try {
            //HashMap<String, Object> storeLoad = ConnectorAPI.HookManager().loadData("store_and_retrieve.yml");

            String route = (String) ConnectorAPI.StoreAndRetrieve().store.get("currentRoute");
            MethodType method = (MethodType) ConnectorAPI.StoreAndRetrieve().store.get("currentMethod");
            Map<String, Object> body = (Map<String, Object>) ConnectorAPI.StoreAndRetrieve().store.get("currentBody");

            if (route == null || method == null) {
                throw new RuntimeException("Route ou méthode non définie. Appelez getRoutes() d'abord.");
            }

            ApiResponse<Void> response;

            if (method == MethodType.GET) {
                response = apiClient.callAPIGet(route).block();
            } else if (method == MethodType.POST) {
                response = apiClient.callAPIPost(route, body).block();
            } else if (method == MethodType.PUT) {
                response = apiClient.callAPIPut(route, body).block();
            } else if (method == MethodType.PATCH) {
                response = apiClient.callAPIPatch(route, body).block();
            } else if (method == MethodType.DELETE) {
                response = apiClient.callAPIDelete(route).block();
            } else {
                ConnectorAPI.Logger().ERROR("Méthode d'envoie non supportée");

                return null;
            }

            // Nettoie le store après utilisation
            ConnectorAPI.StoreAndRetrieve().store.remove("currentRoute");
            ConnectorAPI.StoreAndRetrieve().store.remove("currentMethod");
            ConnectorAPI.StoreAndRetrieve().store.remove("currentBody");

            //ConnectorAPI.HookManager().saveData(ConnectorAPI.StoreAndRetrieve().store, "store_and_retrieve.yml");

            return response;

        } catch (Exception e) {
            ConnectorAPI.Logger().ERROR("Erreur lors de l'exécution de la requête: " + e.getMessage());
            throw new RuntimeException("Erreur lors de l'exécution de la requête", e);
        }
    }
}
