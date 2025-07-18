package fr.sandro642.github.test;

import fr.sandro642.github.ConnectLib;

import fr.sandro642.github.api.ApiFactory;
import fr.sandro642.github.jobs.misc.MethodType;
import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.jobs.misc.VersionType;
import fr.sandro642.github.utils.ConvertEnum;
import fr.sandro642.github.utils.YamlUtils;
import org.junit.jupiter.api.Test;

/**
 * MainTest is a test class for the ConnectLib library.
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
        ConnectLib.initialize(ResourceType.TEST_RESOURCES);
    }


    public static void main(String[] args) {
        ConnectLib.initialize(ResourceType.TEST_RESOURCES, TestRoutes.class);

        try {

            ApiFactory response = ConnectLib.JobGetInfos()
                    .getRoutes(VersionType.V1_BRANCH, MethodType.GET, TestRoutes.VERSION)
                    .getResponse();

            System.out.println(response.getSpecData("data", "version"));

            System.out.println(ConnectLib.YamlUtils().isLogEnabled());

        } catch (Exception e) {
            return;
        }
    }

    @Test
    public void testUseFullRoute() {
        ConnectLib.initialize(ResourceType.TEST_RESOURCES, TestRoutes.class);

        try {
            ApiFactory response = ConnectLib.JobGetInfos()
                    .getRoutes(VersionType.V1_BRANCH, MethodType.POST, TestRoutes.INFO, null, null)
                    .getResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
