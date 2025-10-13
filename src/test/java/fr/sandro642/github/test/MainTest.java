package fr.sandro642.github.test;

import fr.sandro642.github.ConnectLib;

import fr.sandro642.github.api.ApiFactory;
import fr.sandro642.github.enums.LangType;
import fr.sandro642.github.enums.MethodType;
import fr.sandro642.github.enums.ResourceType;
import fr.sandro642.github.jobs.RouteImport;
import fr.sandro642.github.jobs.URLProvider;
import fr.sandro642.github.jobs.VersionProvider;
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

    private static ConnectLib connectLib = new ConnectLib();

    public enum TestRoutes implements RouteImport {
        HELLO("/hello"),
        GREET("/greet$name$")
        ;

        final String route;

        TestRoutes(String route) {
            this.route = route;
        }

        @Override
        public String getRoute() {
            return route;
        }
    }

    public enum TestUrl implements URLProvider {
        LOCALHOST("http://localhost:8080")
        ;

        private final String url;

        TestUrl(String url) {
            this.url = url;
        }

        @Override
        public String getURL() {
            return url;
        }
    }

    public enum TestCustomVersion implements VersionProvider {
        DEV_BRANCH("dev"),
        ;

        private final String version;

        TestCustomVersion(String version) {
            this.version = version;
        }

        @Override
        public String getVersion() {
            return version;
        }
    }

    @Test
    public void initializeCAPI() {
        connectLib.Init(ResourceType.TEST_RESOURCES, LangType.ENGLISH, TestRoutes.class);
    }


    public static void main(String[] args) {
        connectLib.Init(ResourceType.TEST_RESOURCES, LangType.FRENCH, TestRoutes.class);
        try {
            connectLib.Logger().showLogs();

             CompletableFuture<ApiFactory> apiFactoryCompletableFuture = connectLib.JobGetInfos()
                    .getRoutes(MethodType.GET, TestRoutes.HELLO)
                    .getResponse();

             ApiFactory response = apiFactoryCompletableFuture.get(5, TimeUnit.SECONDS);

            System.out.println("Response: " + response.display());


        } catch (Exception e) {
            return;
        }
    }


    @Test
    public void testUseFullRoute() {
        connectLib.Init(ResourceType.TEST_RESOURCES, LangType.FRENCH, TestRoutes.class);

        connectLib.Logger().showLogs();
        try {
            Map<String, String> query = Map.of(
                    "name", "Sandro642"
            );

            CompletableFuture<ApiFactory> factoryCompletableFuture = connectLib.JobGetInfos()
                    .getRoutes(MethodType.GET, TestRoutes.GREET, null, null, query)
                    .getResponse();

            ApiFactory response = factoryCompletableFuture.get(5, TimeUnit.SECONDS);

            System.out.println("Response: " + response.display());
            System.out.println("Status Code: " + response.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLangType() {
        connectLib.Init(ResourceType.TEST_RESOURCES, LangType.ENGLISH, TestRoutes.class);

        connectLib.Logger().showLogs();

        try {
            CompletableFuture<ApiFactory> factoryCompletableFuture = connectLib.JobGetInfos()
                    .getRoutes(MethodType.GET, TestRoutes.HELLO)
                    .getResponse();

            ApiFactory response = factoryCompletableFuture.get(5, TimeUnit.SECONDS);

            System.out.println("Response: " + response.display());
            System.out.println("Status Code: " + response.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
