package fr.sandro642.github.test;

import fr.sandro642.github.ConnectLib;

import fr.sandro642.github.api.ApiFactory;
import fr.sandro642.github.enums.LangType;
import fr.sandro642.github.enums.MethodType;
import fr.sandro642.github.enums.ResourceType;
import fr.sandro642.github.provider.AtomicFactory;
import fr.sandro642.github.provider.RouteImport;
import fr.sandro642.github.provider.URLProvider;
import fr.sandro642.github.provider.VersionProvider;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * MainTest is a test class for the ConnectLib library.
 * @author Sandro642
 * @version 1.0
 */

public class MainTest {

    private static final ConnectLib connectLib = new ConnectLib();

    public enum TestRoutes implements RouteImport {
        HELLO("/hello"),
        GREET("greet$name$"),
        REQUEST_TOKEN("/auth/request/token")
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

    public class ClassheritFromFactory extends AtomicFactory {



        public ClassheritFromFactory(ApiFactory apiFactory) {
            getPhysx(apiFactory);
        }

        public Object getContent() {
            return rawPhysx().get("content");
        }
    }

    /**
     * Example of URL branches, you can add multiple branches if you have multiple environments (dev, prod, etc.)
     */
    public enum ExampleUrlBranch implements URLProvider {
        LOCALHOST("http://localhost:8080"),
        POST_PROD("https://post-prod.systemsolutiongroup.xyz/"),
        PROD("https://api.safe-project.systemsolutiongroup.xyz/")
        ;

        private final String url;

        ExampleUrlBranch(String url) {
            this.url = url;
        }

        @Override
        public String getURL() {
            return url;
        }
    }

    public enum TestCustomVersion implements VersionProvider {
        DEV_BRANCH("dev"),
        V1_API("v1/api")
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
        connectLib.init(ResourceType.TEST_RESOURCES, LangType.ENGLISH, TestRoutes.class);
    }


    public static void main(String[] args) {
        connectLib.init(ResourceType.TEST_RESOURCES, LangType.FRENCH, TestRoutes.class);
        try {
            connectLib.Logger().showLogs();

             CompletableFuture<ApiFactory> apiFactoryCompletableFuture = connectLib.JobGetInfos()
                    .getRoutes(MethodType.GET, TestRoutes.HELLO)
                    .execute();

             ApiFactory response = apiFactoryCompletableFuture.get(5, TimeUnit.SECONDS);

            System.out.println("Response: " + response.display());


        } catch (Exception e) {
        }
    }


    @Test
    public void testUseFullRoute() {
        connectLib.init(ResourceType.TEST_RESOURCES, LangType.FRENCH, TestRoutes.class);

        connectLib.Logger().showLogs();
        try {
            Map<String, String> query = Map.of(
                    "name", "Sandro642"
            );

            CompletableFuture<ApiFactory> factoryCompletableFuture = connectLib.JobGetInfos()
                    .getRoutes(MethodType.GET, TestRoutes.GREET, null, null, query)
                    .execute();

            ApiFactory response = factoryCompletableFuture.get(5, TimeUnit.SECONDS);

            System.out.println("Response: " + response.display());
            System.out.println("Status Code: " + response.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLangType() {
        connectLib.init(ResourceType.TEST_RESOURCES, LangType.ENGLISH, TestRoutes.class);

        connectLib.Logger().showLogs();

        try {
            CompletableFuture<ApiFactory> factoryCompletableFuture = connectLib.JobGetInfos()
                    .getRoutes(TestCustomVersion.DEV_BRANCH, MethodType.GET, TestRoutes.HELLO)

                    /**
                     * You can change the URL of the API here if you want to use another URL than the one in the config file.
                     */
                    //.urlBranch(ExampleUrlBranch.LOCALHOST)

                    .execute();

            ApiFactory response = factoryCompletableFuture.get(5, TimeUnit.SECONDS);

            System.out.println("Response: " + response.display());
            System.out.println("Status Code: " + response.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testSpecData() {
        try {

            connectLib.init(ResourceType.MAIN_RESOURCES, LangType.ENGLISH, TestRoutes.class);

            CompletableFuture<ApiFactory> apiFactoryCompletableFuture = connectLib.JobGetInfos()
                    .getRoutes(TestCustomVersion.V1_API, MethodType.GET, TestRoutes.REQUEST_TOKEN)
                    .urlBranch(ExampleUrlBranch.PROD)
                    .execute();

            ApiFactory apiFactory = apiFactoryCompletableFuture.get(5, TimeUnit.SECONDS);

            System.out.println("Réponse brute: " + apiFactory.display());

            System.out.println("Data: " + apiFactory.getData("data"));

            System.out.println(apiFactory.getSpecData("data", "accessToken"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void startAppServices() {
        try {
            connectLib.Logger().showLogs();
            connectLib.init(ResourceType.TEST_RESOURCES, LangType.ENGLISH, TestRoutes.class);
                    //.webServices(8080, "TestDashboard");

            CompletableFuture<ClassheritFromFactory> apiFactoryCompletableFuture = connectLib.JobGetInfos()
                    .getRoutes(MethodType.GET, TestRoutes.HELLO)
                    .urlBranch(ExampleUrlBranch.POST_PROD)
                    .execute()
                    .thenApply(ClassheritFromFactory::new);

            ClassheritFromFactory classheritFromFactory = apiFactoryCompletableFuture.get(5, TimeUnit.SECONDS);

            System.out.println("Response: " + classheritFromFactory.getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
