package fr.sandro642.github.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.sandro642.github.ConnectorAPI;
import fr.sandro642.github.jobs.misc.ResourceType;

public class Logs {

    /**
     * Création de l'instance de Logs
     */
    private static Logs instance;

    /**
     * Création de la variable pathFile qui va contenir le path du ResourceType
     */
    private static String pathFile;

    /**
     * Création d'une ArrayList qui va contenir les logs en mémoire
     */
    private static List<String> logBuffer = new ArrayList<>();

    /**
     * Création de la fonction qui permet de mettre le path du ResourceType
     * correspondant.
     */
    public void setPathFile(ResourceType type) {
        this.pathFile = type.getPath();
    }

    /**
     * Ajout d'un log dans le fichier logs.
     *
     * @param logText
     * @param logType
     * @param <P>
     */
    public <P> void MakeALog(String logText, P logType) {
        try {
            Date date = new Date();
            SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = timestampFormat.format(date);

            // Créer le message de log
            StringBuilder logContent = new StringBuilder();
            logContent.append("[").append(timestamp).append("] ");
            logContent.append("[").append(logType.toString().toUpperCase()).append("] ");
            logContent.append(logText).append("\n");

            // Ajouter le message au buffer en mémoire
            logBuffer.add(logContent.toString());

            if (ConnectorAPI.YamlUtils().isLogEnabled()) {

                // Si c'est CRITICAL ou ERROR, créer le fichier avec tous les messages
                if (logType.toString().toUpperCase().equals("CRITICAL") || logType.toString().toUpperCase().equals("ERROR")) {
                    File directory = new File(pathFile, "logs");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                    String dateTimeStr = fileNameFormat.format(date);
                    File file = new File(directory, "app_" + dateTimeStr + ".log");

                    try (FileWriter writer = new FileWriter(file)) {
                        // Écrire tous les messages du buffer dans le fichier
                        for (String logEntry : logBuffer) {
                            writer.write(logEntry);
                        }

                        // Vider le buffer après avoir écrit dans le fichier
                        logBuffer.clear();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Création d'un getLogs pour l'instance de logs
     */
    public static Logs getLogs() {
        return instance;
    }
}
