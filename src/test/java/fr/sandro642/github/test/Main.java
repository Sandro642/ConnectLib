package fr.sandro642.github.test;

import fr.sandro642.github.ConnectorAPI;

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
    public void testYmlFile() {
        ConnectorAPI.JobTemplate().generateTemplateIfNotExists(ResourceType.TEST_RESOURCES);

    }

    @Test
    public void testSeeDataMap() {
        System.out.println(ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().URL_KEY));
    }

    @Test
    public void testGetUrl() {
        System.out.println(ConnectorAPI.JobGetInfos().getURL(ResourceType.TEST_RESOURCES));
    }

    @Test
    public void testGetRoutes() {
         ConnectorAPI.JobGetInfos().getRoutes(VersionType.V1_BRANCH, "addMoney");
    }
}
