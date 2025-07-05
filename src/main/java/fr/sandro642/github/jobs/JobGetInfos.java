package fr.sandro642.github.jobs;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.jobs.misc.ResourceType;
import fr.sandro642.github.jobs.misc.VersionType;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JobGetInfos {
    /**
     * Création de l'instance de JobGetRoutes.
     * @see JobGetInfos#getInstance()
     */
    private static JobGetInfos instance;

    /**
     * Méthode pour récupérer l'URL du serveur à partir du fichier infos.yml.
     * @return L'URL du serveur ou null si la clé n'existe pas.
     */
    public static String getURL(ResourceType resourceType) {
        String yamlFilePath = resourceType.getPath() + "/infos.yml";

        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);

            // Supposons que urlPath est à la racine du fichier YAML
            return (String) yamlData.get("urlPath");
        } catch (Exception ex) {
            return null; // Retourne null si une exception est levée ou si la clé n'existe pas
        }
    }

    /**
     * Méthode pour récupérer les routes à partir du fichier infos.yml.
     * @return Les routes sous forme de chaîne de caractères ou null si la clé n'existe pas.
     */
    public String getRoutes(VersionType versionType, String routeName) {
        String yamlFilePath = ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().URL_KEY) + "/infos.yml";
        System.out.println("Test dans getRoutes : " + ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().URL_KEY) + " YamlPath : " + yamlFilePath);

        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);

            // Récupérer la map "routes"
            Map<String, Object> routes = (Map<String, Object>) yamlData.get("routes");
            if (routes != null) {
                return versionType.getVersion() + (String) routes.get(routeName);
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Création du getter de l'instance de JobGetRoutes.
     * @return L'instance de JobGetRoutes.
     */
    public static JobGetInfos getInstance() {
        if (instance == null) {
            instance = new JobGetInfos();
        }
        return instance;
    }
}