package fr.sandro642.github.test;

import fr.sandro642.github.ConnectorAPI;

import fr.sandro642.github.api.ApiResponse;
import fr.sandro642.github.jobs.misc.MethodType;
import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.jobs.misc.VersionType;
import org.junit.jupiter.api.Test;

/**
 * Main est une classe de test pour vérifier la création du fichier YML et pleins d'autres fonctionnalités de la librairie.
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class Main {

    @Test
    public void initializeCAPI() {
        ConnectorAPI.initialize(ResourceType.TEST_RESOURCES);
    }


    public static void main(String[] args) {
        ConnectorAPI.initialize(ResourceType.TEST_RESOURCES);

        try {
            // Exemple d'utilisation comme demandé
            ApiResponse<Void> response = ConnectorAPI.JobGetInfos()
                    .getRoutes(VersionType.V1_BRANCH, MethodType.GET, "version")
                    .getResponse();

            System.out.println("Data: " + response.getData());
            System.out.println("Message: " + response.getMsg());
            System.out.println("Code: " + response.getCode());
            System.out.println("Erreur: " + response.isErr());

            System.out.println(response.display());

            try {
                System.out.println("Valeur spécifique: " + response.getSpecDataString("version"));
            } catch (Exception e) {
                System.out.println("Clé 'version' non trouvée dans les données");
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel API: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Test
    public void testGetExtraData() {
        ConnectorAPI.initialize(ResourceType.TEST_RESOURCES);

        try {
            ApiResponse<Void> response = ConnectorAPI.JobGetInfos()
                    .getRoutes(VersionType.V1_BRANCH, MethodType.GET, "example")
                    .getResponse();

            System.out.println("Data: " + response);

        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel API: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
