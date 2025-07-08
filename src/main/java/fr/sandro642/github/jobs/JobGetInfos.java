package fr.sandro642.github.jobs;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.api.ApiClient;
import fr.sandro642.github.api.ApiResponse;
import fr.sandro642.github.jobs.misc.MethodType;
import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.jobs.misc.VersionType;
import fr.sandro642.github.utils.YamlUtils;

import java.util.Map;

public class JobGetInfos {

    private ApiClient apiClient;
    private YamlUtils yamlUtils;

    public JobGetInfos() {
        this.apiClient = new ApiClient(ResourceType.MAIN_RESOURCES);
        this.yamlUtils = ConnectorAPI.YamlUtils();
    }

    /**
     * Récupère les routes depuis le fichier YAML et construit l'URL complète
     * @param versionType Version de l'API (V1_BRANCH, V2_BRANCH)
     * @param methodType Type de méthode HTTP (GET, POST)
     * @param routeName Nom de la route dans le fichier YAML
     * @return JobGetInfos pour chaînage
     */
    public JobGetInfos getRoutes(VersionType versionType, MethodType methodType, String routeName) {
        return getRoutes(versionType, methodType, routeName, null);
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
        try {

            // Récupère la route depuis le fichier YAML
            String route = yamlUtils.getRoute(routeName);

            ConnectorAPI.Logger().INFO("Route " + routeName + " + " + route);

            // Construit l'URL complète avec la version
            String fullRoute = "/" + versionType.getVersion() + route;

            ConnectorAPI.Logger().INFO("Full route " + fullRoute);

            // Stocke les informations pour la requête
            ConnectorAPI.StoreAndRetrieve().store.put("currentRoute", fullRoute);
            ConnectorAPI.StoreAndRetrieve().store.put("currentMethod", methodType);

            if (body != null) {
                ConnectorAPI.StoreAndRetrieve().store.put("currentBody", body);
            }

            ConnectorAPI.Logger().INFO("Route construite: " + fullRoute);

        } catch (Exception e) {
            ConnectorAPI.Logger().ERROR("Erreur lors de la construction de la route: ");
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
            } else {
                throw new RuntimeException("Méthode HTTP non supportée: " + method);
            }

            // Nettoie le store après utilisation
            ConnectorAPI.StoreAndRetrieve().store.remove("currentRoute");
            ConnectorAPI.StoreAndRetrieve().store.remove("currentMethod");
            ConnectorAPI.StoreAndRetrieve().store.remove("currentBody");

            return response;

        } catch (Exception e) {
            ConnectorAPI.Logger().ERROR("Erreur lors de l'exécution de la requête: " + e.getMessage());
            throw new RuntimeException("Erreur lors de l'exécution de la requête", e);
        }
    }
}
