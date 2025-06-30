package fr.sandro642.github.connect;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Dans cette classe il va y avoir tout le code pour gérer et traiter les informations pour la connexion à l'API.
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class JobConnexion {
    // Création de l'instance de la classe JobConnexion
    private static JobConnexion instance;

//    public static void generateTemplateIfNotExists(String path) {
//        File file = new File(path);
//        if (!file.exists()) {
//            String template =
//                    "# properties Connector API By Sandro642\n\n" +
//                            "urlPath: \n\n" +
//                            "routes:\n" +
//                            "  # addMoney:\n" +
//                            "  #   url: \"api/v1/add/money/user\"\n" +
//                            "  # removeMoney:\n" +
//                            "  #   url: \"api/v2/remove/money/user\"\n";
//            try (FileWriter writer = new FileWriter(file)) {
//                writer.write(template);
//            } catch (IOException e) {
//                throw new RuntimeException("Erreur lors de la création du template bref.yml", e);
//            }
//        }
//    }

    // Création du getteur de l'instance
    public static JobConnexion getInstance() {
        if (instance == null) {
            // Si l'instance est nulle, on la crée
            instance = new JobConnexion();
        }
        // On retourne l'instance
        return instance;
    }
}
