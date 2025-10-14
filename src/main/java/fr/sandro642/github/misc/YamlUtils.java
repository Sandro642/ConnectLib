package fr.sandro642.github.misc;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.enums.lang.CategoriesType;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * YamlUtils is a utility class for handling YAML files in the ConnectLib library.
 * It provides methods to read configuration values such as the base URL, logging settings, and routes from a YAML file.
 *
 * @author Sandro642
 * @version 1.0
 */
public class YamlUtils {

    private ConnectLib connectLib = new ConnectLib();

    /**
     * Gets the base URL from the YAML configuration file.
     * This method reads the `infos.yml` file located in the directory specified by the
     * @return the base URL as a String, or null if an error occurs.
     */
    public String getURL() {
        String yamlFilePath = connectLib.StoreAndRetrieve().store.get(connectLib.StoreAndRetrieve().FILE_LOCATION_KEY)
                + "/infos.yml";

        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);
            return (String) yamlData.get("urlPath");
        } catch (Exception ex) {
            connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.YAMLUTILS_CLASS, "geturl.error", Map.of("file", "infos.yml", "exception", ex.getMessage())));
            return null;
        }
    }

    /**
     * Checks if logging is enabled in the YAML configuration file.
     * This method reads the `infos.yml` file and retrieves the `enableLogs` setting.
     *
     * @return true if logging is enabled, false if disabled, or null if an error occurs.
     */
    public Boolean isLogEnabled() {
        String yamlFilePath = connectLib.StoreAndRetrieve().store.get(connectLib.StoreAndRetrieve().FILE_LOCATION_KEY)
                + "/infos.yml";

        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);
            return (Boolean) yamlData.get("enableLogs");
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Retrieves the routes defined in the YAML configuration file.
     * This method reads the `infos.yml` file and returns a map of routes.
     *
     * @return a map of route names to their corresponding paths, or null if an error occurs.
     */
    public Map<String, String> getRoutes() {
        String yamlFilePath = connectLib.StoreAndRetrieve().store.get(connectLib.StoreAndRetrieve().FILE_LOCATION_KEY)
                + "/infos.yml";

        try (InputStream inputStream = Files.newInputStream(Paths.get(yamlFilePath))) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);

            return (Map<String, String>) yamlData.get("routes");
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Generates a template `infos.yml` file if it does not already exist.
     * If the file exists, it updates the routes section with the provided routes.
     *
     * @param routes a map of route names to their corresponding paths
     */
    public void generateTemplateIfNotExists(Map<Enum<?>, String> routes) {
        String basePath = connectLib.HookManager().BASE_PATH();

        File directory = new File(basePath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(basePath, "infos.yml");

        if (file.exists()) {
            try {
                List<String> lines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }

                int routesStartIndex = -1;

                // Trouver l'index de la ligne "routes:"
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i).trim();
                    if (line.equals("routes:")) {
                        routesStartIndex = i;
                        break;
                    }
                }

                if (routesStartIndex != -1) {
                    List<String> newLines = new ArrayList<>();

                    // Garder tout ce qui vient avant "routes:"
                    newLines.addAll(lines.subList(0, routesStartIndex + 1));

                    // Ajouter les commentaires d'exemple
                    newLines.add("  #info: \"/info/version\"");
                    newLines.add("  #ping: \"/ping\"");
                    newLines.add("  #status: \"/status\"");
                    newLines.add("");

                    // Ajouter les nouvelles routes
                    for (Map.Entry<Enum<?>, String> entry : routes.entrySet()) {
                        newLines.add("  " + entry.getKey().name().toLowerCase() + ": \"" + entry.getValue() + "\"");
                    }

                    // Trouver où se termine la section routes
                    int afterRoutesIndex = lines.size();
                    for (int i = routesStartIndex + 1; i < lines.size(); i++) {
                        String line = lines.get(i).trim();

                        // Si on trouve une ligne qui n'est pas vide, pas un commentaire,
                        // et qui ne commence pas par une indentation (donc pas une route)
                        if (!line.isEmpty() && !line.startsWith("#")) {
                            // Vérifier si c'est une ligne de route (avec indentation)
                            String originalLine = lines.get(i);
                            if (!originalLine.startsWith("  ") && !originalLine.startsWith("\t")) {
                                // C'est le début d'une nouvelle section
                                afterRoutesIndex = i;
                                break;
                            }
                        }
                    }

                    // Ajouter ce qui vient après la section routes (comme # Logs, enableLogs, etc.)
                    if (afterRoutesIndex < lines.size()) {
                        newLines.add("");
                        newLines.addAll(lines.subList(afterRoutesIndex, lines.size()));
                    }

                    // Écrire le fichier mis à jour
                    try (FileWriter writer = new FileWriter(file)) {
                        for (String line : newLines) {
                            writer.write(line + "\n");
                        }
                    }
                }

            } catch (IOException e) {
                connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.YAMLUTILS_CLASS, "gentempifnotexist.error", Map.of("file", "infos.yml", "exception", e.getMessage())));
            }
        } else {
            // Créer un nouveau fichier avec le template
            StringBuilder template = new StringBuilder(
                    "# properties ConnectLib By Sandro642\n\n" +
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

            template.append("\n# Logs\n")
                    .append("enableLogs: true\n");

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(template.toString());

                connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.YAMLUTILS_CLASS, "gentempifnotexist.needed", Map.of("file", "infos.yml")));
            } catch (IOException e) {
                connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.YAMLUTILS_CLASS, "gentempifnotexist.errorcreated", Map.of("file", "infos.yml", "exception", e.getMessage())));
            }
        }
    }
}