package fr.sandro642.github.jobs;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.jobs.misc.ResourceType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Dans cette classe il va y avoir tout le code pour gérer et traiter les informations pour la connexion à l'API.
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 * @see JobTemplate#generateTemplateIfNotExists(ResourceType)
 * @see JobTemplate#getInstance()
 */

public class JobTemplate {
    // Création de l'instance de la classe JobConnexion
    private static JobTemplate instance;

    /**
     * Méthode pour générer un template infos.yml si il n'existe pas déjà.
     * @param type
     */
    public void generateTemplateIfNotExists(ResourceType type) {
        String basePath = type.getPath();
        ConnectorAPI.StoreAndRetrieve().store.put(ConnectorAPI.StoreAndRetrieve().URL_KEY, basePath);
        File file = new File(basePath, "infos.yml");
        if (!file.exists()) {
            String template =
                    "# properties Connector API By Sandro642\n\n" +
                            "urlPath: \n\n" +
                            "routes:\n" +
                            "  # addMoney:\n" +
                            "  #   url: \"api/v1/add/money/user\"\n" +
                            "  # removeMoney:\n" +
                            "  #   url: \"api/v2/remove/money/user\"\n";
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(template);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la création du template infos.yml", e);
            }
        }
    }

    /**
     * Getter de l'instance de la classe JobConnexion.
     * @return instance
     */
    public static JobTemplate getInstance() {
        if (instance == null) {
            // Si l'instance est nulle, on la crée
            instance = new JobTemplate();
        }
        // On retourne l'instance
        return instance;
    }
}
