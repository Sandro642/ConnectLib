package fr.sandro642.github.utils;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.jobs.misc.ResourceType;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * YamlUtils est une classe utilitaire pour gérer les opérations liées aux fichiers YAML dans le contexte de l'API Connector.
 * Elle permet de récupérer des informations depuis un fichier YAML, de générer un template si nécessaire,
 * et de fournir des routes spécifiques définies dans le fichier YAML.
 *
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class YamlUtils {

    /**
     * Récupère l'URL de base depuis le fichier YAML
     * @return l'URL de base définie dans le fichier YAML, ou null si une erreur se produit
     */
    public String getURL() {
        String yamlFilePath = ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().FILE_LOCATION_KEY) + "/infos.yml";

        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);
            return (String) yamlData.get("urlPath");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Récupère une route spécifique depuis le fichier YAML
     * @param routeName
     * @return
     */
    public String getRoute(String routeName) {
        String yamlFilePath = ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().FILE_LOCATION_KEY) + "/infos.yml";
        System.out.println("Test dans getRoutes : " + ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().FILE_LOCATION_KEY) + " YamlPath : " + yamlFilePath);

        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);

            // Récupérer la map "routes"
            Map<String, Object> routes = (Map<String, Object>) yamlData.get("routes");
            if (routes != null) {
                return (String) routes.get(routeName);
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Génère un template de fichier YAML si le fichier n'existe pas déjà.
     * @param type
     */
    public void generateTemplateIfNotExists(ResourceType type) {
        String basePath = type.getPath();
        File directory = new File(basePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(basePath, "infos.yml");
        if (!file.exists()) {
            String template =
                    "# properties Connector API By Sandro642\n\n" +
                            "urlPath: \"https://api.mcas-project.systemsolutiongroup.xyz/api/\"\n\n" +
                            "routes:\n" +
                            "  info: \"/mcas/info/version\"\n" +
                            "  # addMoney:\n" +
                            "  #   url: \"/mcas/add/money/user\"\n" +
                            "  # removeMoney:\n" +
                            "  #   url: \"/mcas/remove/money/user\"\n";
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(template);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la création du template infos.yml", e);
            }
        }
    }
}
