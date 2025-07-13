package fr.sandro642.github.utils;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.hook.MCSupport;
import fr.sandro642.github.jobs.misc.ResourceType;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

    public Map<String, String> getSchemas() {
        String yamlFilePath = ConnectorAPI.StoreAndRetrieve().store.get(ConnectorAPI.StoreAndRetrieve().FILE_LOCATION_KEY) + "/infos.yml";

        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);

            // Récupérer la map "schema"
            return (Map<String, String>) yamlData.get(("schema"));
        } catch (IOException e) {
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

            // Récupérer la map "routes"
            return (Map<String, String>) yamlData.get("routes");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Génère un template de fichier YAML si celui-ci n'existe pas, ou met à jour la section des routes si le fichier existe déjà.
     *
     * @param type   Le type de ressource pour laquelle générer le template.
     * @param routes Les routes à ajouter ou mettre à jour dans le fichier YAML.
     */
    public void generateTemplateIfNotExists(ResourceType type, Map<Enum<?>, String> routes, Map<Enum<?>, String> schemas) {
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

        if (file.exists()) {
            // Le fichier existe, on met à jour les sections routes et schemas
            try {
                // Lire le contenu existant
                List<String> lines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }

                // Trouver les indices de début et fin de la section routes
                int routesStartIndex = -1;
                int routesEndIndex = -1;

                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i).trim();
                    if (line.equals("routes:")) {
                        routesStartIndex = i;
                    } else if (routesStartIndex != -1 && line.matches("^[a-zA-Z_][a-zA-Z0-9_]*:.*")) {
                        // On a trouvé une nouvelle section (pas une route.)
                        if (!line.startsWith("#") && !line.matches("^\\s*[a-zA-Z_][a-zA-Z0-9_]*:\\s*\"/.*")) {
                            routesEndIndex = i;
                            break;
                        }
                    }
                }

                // Si on n'a pas trouvé de fin, la section routes va jusqu'à la fin du fichier
                if (routesStartIndex != -1 && routesEndIndex == -1) {
                    routesEndIndex = lines.size();
                }

                // Trouver les indices de début et fin de la section schema
                int schemaStartIndex = -1;
                int schemaEndIndex = -1;
                boolean enableFound = false;

                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i).trim();
                    if (line.equals("schema:")) {
                        schemaStartIndex = i;
                    } else if (schemaStartIndex != -1 && line.startsWith("enable:")) {
                        enableFound = true;
                    } else if (schemaStartIndex != -1 && enableFound && line.matches("^[a-zA-Z_][a-zA-Z0-9_]*:.*")) {
                        // On a trouvé une nouvelle section après enable
                        if (!line.startsWith("#") && !line.matches("^\\s*[a-zA-Z_][a-zA-Z0-9_]*:\\s*.*")) {
                            schemaEndIndex = i;
                            break;
                        }
                    }
                }

                // Si on n'a pas trouvé de fin, la section schema va jusqu'à la fin du fichier
                if (schemaStartIndex != -1 && schemaEndIndex == -1) {
                    schemaEndIndex = lines.size();
                }

                List<String> newLines = new ArrayList<>();

                if (routesStartIndex != -1) {
                    // Supprimer l'ancienne section routes (garder juste la ligne "routes :")
                    newLines.addAll(lines.subList(0, routesStartIndex + 1));

                    // Ajouter les routes commentées par défaut
                    newLines.add("  #info: \"/info/version\"");
                    newLines.add("  #ping: \"/ping\"");
                    newLines.add("  #status: \"/status\"");
                    newLines.add("");

                    // Ajouter les nouvelles routes
                    for (Map.Entry<Enum<?>, String> entry : routes.entrySet()) {
                        newLines.add("  " + entry.getKey().name().toLowerCase() + ": \"" + entry.getValue() + "\"");
                    }

                    // Ajouter le début de la section schema jusqu'à enable
                    if (schemaStartIndex != -1) {
                        newLines.add("");
                        newLines.add("schema:");
                        newLines.add("    #Activer la création de schéma ?");
                        newLines.add("    enable: false");
                        newLines.add("");

                        // Ajouter les schémas
                        for (Map.Entry<Enum<?>, String> entry : schemas.entrySet()) {
                            newLines.add("    " + entry.getKey().name().toLowerCase() + ": \"" + entry.getValue() + "\"");
                        }

                        // Ajouter les commentaires par défaut
                        newLines.add("");
                        newLines.add("    #Schéma par défaut:");
                        newLines.add("    #\tmsg : string");
                        newLines.add("    #\terr: boolean");
                        newLines.add("    #\tcode: integer");
                        newLines.add("    #\tdata: Map<String, Object>");
                        newLines.add("");
                        newLines.add("    #Composants à créer exemple, je vais créer plusieurs composant:");
                        newLines.add("    #         msg : str");
                        newLines.add("    #         status : bln");
                        newLines.add("    #         code : int");
                        newLines.add("    #         data_string-object : map / string pour une chaine de caractère et");
                        newLines.add("    #                                  / object pour la récupération de n'importe");
                        newLines.add("    #                                  / quel type de variable.");
                        newLines.add("    #");
                        newLines.add("    # Grâce à cela vous pourrez les appeler pour récupérer vos propres valeurs");
                        newLines.add("    # par rapport à votre schéma réponse API");

                        // Ajouter le reste du fichier après la section schema
                        if (schemaEndIndex < lines.size()) {
                            newLines.add("");
                            newLines.addAll(lines.subList(schemaEndIndex, lines.size()));
                        }
                    } else {
                        // Pas de section schema existante, on l'ajoute
                        newLines.add("");
                        newLines.add("schema:");
                        newLines.add("    #Activer la création de schéma ?");
                        newLines.add("    enable: false");
                        newLines.add("");

                        // Ajouter les schémas
                        for (Map.Entry<Enum<?>, String> entry : schemas.entrySet()) {
                            newLines.add("    " + entry.getKey().name().toLowerCase() + ": \"" + entry.getValue() + "\"");
                        }

                        // Ajouter le reste du fichier
                        if (routesEndIndex < lines.size()) {
                            newLines.add("");
                            newLines.addAll(lines.subList(routesEndIndex, lines.size()));
                        }
                    }

                    // Écrire le fichier mis à jour
                    try (FileWriter writer = new FileWriter(file)) {
                        for (String line : newLines) {
                            writer.write(line + "\n");
                        }
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la mise à jour du fichier infos.yml", e);
            }
        } else {
            // Le fichier n'existe pas, on crée le template complet
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
                    "    enable: false\n\n");

            // Ajouter les schémas
            for (Map.Entry<Enum<?>, String> entry : schemas.entrySet()) {
                template.append("    ")
                        .append(entry.getKey().name().toLowerCase())
                        .append(": \"")
                        .append(entry.getValue())
                        .append("\"\n");
            }

            template.append("\n" +
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
}
