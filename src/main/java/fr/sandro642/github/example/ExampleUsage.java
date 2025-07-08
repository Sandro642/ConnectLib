package fr.sandro642.github.example;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.api.ApiResponse;
import fr.sandro642.github.jobs.misc.MethodType;
import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.jobs.misc.VersionType;

import java.util.HashMap;
import java.util.Map;

/**
 * Exemple d'utilisation de la librairie ConnectorAPI pour récupérer des informations via une API.
 *
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class ExampleUsage {

    /**
     * Point d'entrée de l'application.
     * Démontre toutes les manières d'utiliser la bibliothèque API.
     *
     * @param args Arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        // Initialisation du ConnectorAPI
        ConnectorAPI.initialize(ResourceType.MAIN_RESOURCES);

        try {
            System.out.println("=== DÉMONSTRATION DE LA BIBLIOTHÈQUE API ===\n");

            // 1. Appel GET simple sans paramètres
            System.out.println("1. Appel GET simple :");
            ApiResponse<Void> simpleResponse = ConnectorAPI.JobGetInfos()
                    .getRoutes(MethodType.GET, "info")
                    .getResponse();

            displayResponse(simpleResponse);

            // 2. Appel GET avec version spécifique
            System.out.println("\n2. Appel GET avec version V1 :");
            ApiResponse<Void> versionResponse = ConnectorAPI.JobGetInfos()
                    .getRoutes(VersionType.V1_BRANCH, MethodType.GET, "info")
                    .getResponse();

            displayResponse(versionResponse);

            // 3. Appel POST avec body
            System.out.println("\n3. Appel POST avec body :");
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("username", "admin");
            requestBody.put("password", "password123");
            requestBody.put("remember", true);

            ApiResponse<Void> postResponse = ConnectorAPI.JobGetInfos()
                    .getRoutesWithBody(MethodType.POST, "login", requestBody)
                    .getResponse();

            displayResponse(postResponse);

            // 4. Appel GET avec paramètres d'URL
            System.out.println("\n4. Appel GET avec paramètres d'URL :");
            Map<String, Object> urlParams = new HashMap<>();
            urlParams.put("userId", "12345");
            urlParams.put("token", "abc123xyz");

            ApiResponse<Void> paramsResponse = ConnectorAPI.JobGetInfos()
                    .getRoutesWithParams(MethodType.GET, "user_profile", urlParams)
                    .getResponse();

            displayResponse(paramsResponse);

            // 5. Appel POST avec version, body et paramètres
            System.out.println("\n5. Appel POST complet (version + body + paramètres) :");
            Map<String, Object> updateBody = new HashMap<>();
            updateBody.put("name", "John Doe");
            updateBody.put("email", "john.doe@example.com");
            updateBody.put("active", true);

            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("userId", "67890");
            updateParams.put("sessionId", "sess_abc123");

            ApiResponse<Void> completeResponse = ConnectorAPI.JobGetInfos()
                    .getRoutes(VersionType.V2_BRANCH, MethodType.POST, "update_user", updateBody, updateParams)
                    .getResponse();

            displayResponse(completeResponse);

            // 6. Appel GET avec plusieurs paramètres d'URL
            System.out.println("\n6. Appel GET avec plusieurs paramètres :");
            Map<String, Object> multiParams = new HashMap<>();
            multiParams.put("category", "electronics");
            multiParams.put("subcategory", "smartphones");
            multiParams.put("brand", "apple");
            multiParams.put("model", "iphone15");

            ApiResponse<Void> multiParamsResponse = ConnectorAPI.JobGetInfos()
                    .getRoutesWithParams(MethodType.GET, "product_details", multiParams)
                    .getResponse();

            displayResponse(multiParamsResponse);

            // 7. Appel avec gestion d'erreur spécifique
            System.out.println("\n7. Appel avec gestion d'erreur :");
            try {
                ApiResponse<Void> errorResponse = ConnectorAPI.JobGetInfos()
                        .getRoutes(VersionType.V1_BRANCH, MethodType.GET, "nonexistent_route")
                        .getResponse();

                displayResponse(errorResponse);

            } catch (Exception e) {
                System.err.println("Erreur capturée : " + e.getMessage());
            }

            // 8. Appel avec différents types de données dans le body
            System.out.println("\n8. Appel POST avec types de données variés :");
            Map<String, Object> complexBody = new HashMap<>();
            complexBody.put("stringValue", "Hello World");
            complexBody.put("intValue", 42);
            complexBody.put("boolValue", false);
            complexBody.put("doubleValue", 3.14159);

            // Sous-objet
            Map<String, Object> subObject = new HashMap<>();
            subObject.put("nestedString", "Nested Value");
            subObject.put("nestedInt", 100);
            complexBody.put("nestedObject", subObject);

            // Array
            java.util.List<String> arrayValue = java.util.Arrays.asList("item1", "item2", "item3");
            complexBody.put("arrayValue", arrayValue);

            ApiResponse<Void> complexResponse = ConnectorAPI.JobGetInfos()
                    .getRoutesWithBody(MethodType.POST, "complex_data", complexBody)
                    .getResponse();

            displayResponse(complexResponse);

            // 9. Appel avec paramètres et version sans body
            System.out.println("\n9. Appel GET avec version et paramètres :");
            Map<String, Object> versionParams = new HashMap<>();
            versionParams.put("apiKey", "sk-1234567890");
            versionParams.put("format", "json");

            ApiResponse<Void> versionParamsResponse = ConnectorAPI.JobGetInfos()
                    .getRoutes(VersionType.V2_BRANCH, MethodType.GET, "api_status", null, versionParams)
                    .getResponse();

            displayResponse(versionParamsResponse);

            // 10. Appel sans version (utilise la route telle quelle)
            System.out.println("\n10. Appel sans version :");
            ApiResponse<Void> noVersionResponse = ConnectorAPI.JobGetInfos()
                    .getRoutes(null, MethodType.GET, "health", null, null)
                    .getResponse();

            displayResponse(noVersionResponse);

            System.out.println("\n=== FIN DE LA DÉMONSTRATION ===");

        } catch (Exception e) {
            System.err.println("Erreur générale lors de l'exécution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Méthode utilitaire pour afficher les résultats d'une réponse API
     * @param response La réponse API à afficher
     */
    private static void displayResponse(ApiResponse<Void> response) {
        if (response != null) {
            System.out.println("  Code: " + response.getCode());
            System.out.println("  Message: " + response.getMsg());
            System.out.println("  Erreur: " + response.isErr());
            System.out.println("  Data: " + response.getData());

            // Tentative d'accès à des données spécifiques
            try {
                Object specificData = response.getSpecData("version");
                if (specificData != null) {
                    System.out.println("  Version spécifique: " + specificData);
                }
            } catch (Exception e) {
                // Ignore si la clé n'existe pas
            }

            try {
                Object statusData = response.getSpecData("status");
                if (statusData != null) {
                    System.out.println("  Status spécifique: " + statusData);
                }
            } catch (Exception e) {
                // Ignore si la clé n'existe pas
            }
        } else {
            System.out.println("  Réponse null reçue");
        }
    }
}
