package fr.sandro642.github.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.jobs.misc.ResourceType;

/**
 * Logs is a utility class for managing logging in the ConnectLib library.
 * It provides methods to create logs with timestamps and log types, and to write them to a file.
 *
 * @author Sandro642
 * @version 1.0
 */

public class Logs {

    /**
     * Make instance of Logs
     */
    private static Logs instance;

    /**
     * Constructor of Logs
     * This constructor is private to ensure that the class is a singleton.
     */
    private static String pathFile;

    /**
     * Make list for save logs in memory before writing to file.
     * This buffer is used to store log messages temporarily before they are written to a file.
     */
    private static List<String> logBuffer = new ArrayList<>();

    /**
     * Static block to initialize the Logs instance.
     * This block is executed when the class is loaded, ensuring that the instance is created only once.
     */
    public void setPathFile(ResourceType type) {
        this.pathFile = type.getPath();
    }

    /**
     * * Static block to initialize the Logs instance.
     * This block is executed when the class is loaded, ensuring that the instance is created only once.
     * @param type The type of resource for which the logs are being created.
     * @param <P>
     * @param logText The text to be logged.
     */
    public <P> void MakeALog(String logText, P logType) {
        try {
            Date date = new Date();
            SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = timestampFormat.format(date);

            StringBuilder logContent = new StringBuilder();
            logContent.append("[").append(timestamp).append("] ");
            logContent.append("[").append(logType.toString().toUpperCase()).append("] ");
            logContent.append(logText).append("\n");

            logBuffer.add(logContent.toString());

            if (ConnectLib.YamlUtils().isLogEnabled()) {

                if (logType.toString().toUpperCase().equals("CRITICAL") || logType.toString().toUpperCase().equals("ERROR")) {
                    File directory = new File(pathFile, "logs");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    SimpleDateFormat fileNameFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                    String dateTimeStr = fileNameFormat.format(date);
                    File file = new File(directory, "app_" + dateTimeStr + ".log");

                    try (FileWriter writer = new FileWriter(file)) {
                        for (String logEntry : logBuffer) {
                            writer.write(logEntry);
                        }

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
     * Get the instance of Logs.
     * @return instance of Logs
     */
    public static Logs getLogs() {
        return instance;
    }
}
