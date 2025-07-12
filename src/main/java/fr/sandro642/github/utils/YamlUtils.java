package fr.sandro642.github.utils;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.hook.MCSupport;
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

    public Boolean isSchemaEnabled() {

        String yamlFilePath = ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().FILE_LOCATION_KEY) + "/infos.yml";

        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);

            // Récupérer la map "routes"
            Map<String, Object> schema = (Map<String, Object>) yamlData.get("schema");
            if (schema != null) {
                return (Boolean) schema.get("enable");
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Récupère toutes les routes définies dans le fichier YAML
     * @return une map contenant les routes, ou null en cas d'erreur
     */
    public Map<String,String> getRoutes() {

        String yamlFilePath = ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().FILE_LOCATION_KEY) + "/infos.yml";

        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);

            System.out.println("Récupération des routes " + yamlData.get("routes"));

            // Récupérer la map "routes"
            return (Map<String, String>) yamlData.get("routes");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Génère un template de fichier YAML si le fichier n'existe pas déjà.
     * @param type
     */
    public void generateTemplateIfNotExists(ResourceType type, Map<Enum<?>, String> routes) {
        String basePath;

        if (type == ResourceType.MC_RESOURCES) {
            basePath = MCSupport.getInstance().getPluginPath();
        } else {
            basePath = type.getPath();
        }

        File directory = new File(basePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(basePath, "infos.yml");

        StringBuilder template = new StringBuilder(
                "# properties Connector API By Sandro642\n\n" +
                        "urlPath: \"http://localhost:8080/api\"\n\n" +
                        "routes:\n" +
                        "  #info: \"/info/version\"\n" +
                        "  #ping: \"/ping\"\n" +
                        "  #status: \"/status\"\n\n");

        for (Map.Entry<Enum<?>, String> entry : routes.entrySet()) {
            template.append("  ")
                    .append(entry.getKey().name().toLowerCase())
                    .append(": \"")
                    .append(entry.getValue())
                    .append("\"\n");
        }

        template.append("\nschema:\n" +
                "    #Activer la création de schéma ?\n" +
                "    enable: false\n\n" +
                "    #Schéma par défaut:\n" +
                "    #\tmsg : string\n" +
                "    #\terr: boolean\n" +
                "    #\tcode: integer\n" +
                "    #\tdata: Map<String, Object>\n\n" +
                "    #Composants à créer exemple, je vais créer plusieurs composant:\n" +
                "    #         msg : str\n" +
                "    #         status : bln\n" +
                "    #         code : int\n" +
                "    #         data_string-object : map / string pour une chaine de caractère et\n" +
                "    #                                  / object pour la récupération de n'importe\n" +
                "    #                                  / quel type de variable.\n" +
                "    #\n" +
                "    # Grâce à cela vous pourrez les appeler pour récupérer vos propres valeurs\n" +
                "    # par rapport à votre schéma réponse API\n");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(template.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la création du template infos.yml", e);
        }
    }
}
