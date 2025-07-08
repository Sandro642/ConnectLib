package fr.sandro642.github.example;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.api.ApiResponse;
import fr.sandro642.github.jobs.misc.MethodType;
import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.jobs.misc.VersionType;

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
     * Initialise le ConnectorAPI et effectue un appel API pour récupérer des informations.
     *
     * @param args Arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        // Initialisation du ConnectorAPI
        ConnectorAPI.initialize(ResourceType.MAIN_RESOURCES);

        try {
            // Exemple d'utilisation comme demandé
            ApiResponse<Void> response = ConnectorAPI.JobGetInfos()
                    .getRoutes(VersionType.V1_BRANCH, MethodType.GET, "info")
                    .getResponse();

            System.out.println("Data: " + response.getData());
            System.out.println("Message: " + response.getMsg());
            System.out.println("Code: " + response.getCode());
            System.out.println("Erreur: " + response.isErr());

            // Exemple avec des données spécifiques (si elles existent)
            try {
                System.out.println("Valeur spécifique: " + response.getSpecData("version"));
            } catch (Exception e) {
                System.out.println("Clé 'version' non trouvée dans les données");
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel API: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
