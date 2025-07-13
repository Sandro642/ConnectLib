package fr.sandro642.github.test;

import fr.sandro642.github.ConnectorAPI;

import fr.sandro642.github.api.ApiFactory;
import fr.sandro642.github.jobs.misc.MethodType;
import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.jobs.misc.VersionType;
import fr.sandro642.github.utils.ConvertEnum;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Main est une classe de test pour vérifier la création du fichier YML et pleins d'autres fonctionnalités de la librairie.
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class MainTest {

    public enum TestRoutes implements ConvertEnum.RouteImport {
        VERSION("/api/mcas/info/version"),
        INFO("/api/mcas/info/info"),
        UNIX("/api/auth/link/unix/{sessionId}");

        final String route;

        TestRoutes(String route) {
            this.route = route;
        }

        @Override
        public String route() {
            return route;
        }
    }

    @Test
    public void initializeCAPI() {
        ConnectorAPI.initialize(ResourceType.TEST_RESOURCES);
    }


    public static void main(String[] args) {
        ConnectorAPI.initialize(ResourceType.TEST_RESOURCES, TestRoutes.class);

        try {
            // Exemple d'utilisation comme demandé
            ApiFactory response = ConnectorAPI.JobGetInfos()
                    .getRoutes(VersionType.V1_BRANCH, MethodType.GET, TestRoutes.VERSION)
                    .getResponse();

            System.out.println(response.getSpecData("data", "version"));


//            System.out.println("Data: " + response.getData());
//            System.out.println("Message: " + response.getMsg());
//            System.out.println("Code: " + response.getCode());
//            System.out.println("Erreur: " + response.isErr());
//
//            System.out.println(response.display());

//            try {
//                System.out.println("Valeur spécifique : " + response.getSpecDataString("version"));
//            } catch (Exception e) {
//                System.out.println("Clé 'version' non trouvée dans les données");
//            }

        } catch (Exception e) {
            return;
        }
    }


    @Test
    public void FullObjectTest() {
        ConnectorAPI.initialize(ResourceType.TEST_RESOURCES, TestRoutes.class);

        try {
            ApiFactory response = ConnectorAPI.JobGetInfos()
                    .getRoutes(VersionType.V1_BRANCH, MethodType.GET, "example")
                    .getResponse();
          
        Map<String, Boolean> body = Map.of("status", true);


        Map<String, String> params = Map.of("sessionId", "0233-xgt-7113");

        ApiResponse response = ConnectorAPI.JobGetInfos()
                .getRoutes(VersionType.V1_BRANCH, MethodType.POST, TestRoutes.UNIX, body, params)
                .getResponse();

        System.out.println(response.display());
    }

}
