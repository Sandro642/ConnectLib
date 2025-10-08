package fr.sandro642.github.test;

import fr.sandro642.github.ConnectLib;

import fr.sandro642.github.api.ApiFactory;
import fr.sandro642.github.enums.LangType;
import fr.sandro642.github.enums.MethodType;
import fr.sandro642.github.enums.ResourceType;
import fr.sandro642.github.enums.VersionType;
import fr.sandro642.github.misc.EnumLoader;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * MainTest is a test class for the ConnectLib library.
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class MainTest {

    public enum TestRoutes implements EnumLoader.RouteImport {
        HELLO("/hello"),
        GREET("/greet$name$")
        ;

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
        ConnectLib.initialize(ResourceType.TEST_RESOURCES, LangType.ENGLISH);
    }


    public static void main(String[] args) {
        ConnectLib.initialize(ResourceType.TEST_RESOURCES, LangType.ENGLISH, TestRoutes.class);

        try {

             CompletableFuture<ApiFactory> apiFactoryCompletableFuture = ConnectLib.JobGetInfos()
                    .getRoutes(VersionType.V1_BRANCH, MethodType.GET, TestRoutes.HELLO)
                    .getResponse();

             ApiFactory response = apiFactoryCompletableFuture.get(5, TimeUnit.SECONDS);

            System.out.println("Response: " + response.display());


        } catch (Exception e) {
            return;
        }
    }

    @Test
    public void testUseFullRoute() {
        ConnectLib.initialize(ResourceType.TEST_RESOURCES, LangType.ENGLISH, TestRoutes.class);

        try {
            Map<String, String> query = Map.of(
                    "name", "Sandro642"
            );

            CompletableFuture<ApiFactory> factoryCompletableFuture = ConnectLib.JobGetInfos()
                    .getRoutes(MethodType.GET, TestRoutes.GREET, null, null, query)
                    .getResponse();

            ApiFactory response = factoryCompletableFuture.get(5, TimeUnit.SECONDS);

            System.out.println("Response: " + response.display());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLangType() {
        ConnectLib.initialize(ResourceType.TEST_RESOURCES, LangType.FRENCH, TestRoutes.class);
    }

}
