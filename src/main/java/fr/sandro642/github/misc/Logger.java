package fr.sandro642.github.misc;

/**
 * Logger is a utility class for logging messages in the ConnectLib library.
 * It provides methods to log messages with different severity levels:
 * INFO, WARN, ERROR, and CRITICAL.
 * <p>
 * Use setShowLogs(true/false) to control console output.
 * Logs are always saved to file regardless of the setting.
 *
 * @author Sandro642
 * @version 1.0
 */

public class Logger {

    private final Logs logs = new Logs();
    private static boolean showLogs = false; // Default: logs are hidden

    /**
     * Enable console output for logs.
     * Call this method once to display all logs in console.
     * By default, logs are only saved to file without console output.
     * Logs are always saved to file regardless of this setting.
     */
    public void showLogs() {
        showLogs = true;
    }

    /**
     * Method to display an informational message in the console.
     * This method prints the message in green color if showLogs is enabled,
     * and always logs it to file.
     *
     * @param msg The message to log
     */
    public void INFO(String msg) {
        if (showLogs) {
            // Green
            String INFO = "\u001B[32m[INFO] \u001B[0m";
            System.out.println(INFO + msg);
        }

        logs.MakeALog(msg, "INFO");
    }

    /**
     * Method to display a warning message in the console.
     * This method prints the message in yellow color if showLogs is enabled,
     * and always logs it to file.
     *
     * @param msg The message to log
     */
    public void WARN(String msg) {
        if (showLogs) {
            // Yellow
            String WARN = "\u001B[33m[WARN] \u001B[0m";
            System.out.println(WARN + msg);
        }

        logs.MakeALog(msg, "WARN");
    }

    /**
     * Method to display an error message in the console.
     * This method prints the message in red color if showLogs is enabled,
     * and always logs it to file.
     *
     * @param msg The message to log
     */
    public void ERROR(String msg) {
        if (showLogs) {
            // Red
            String ERROR = "\u001B[31m[ERROR] \u001B[0m";
            System.out.println(ERROR + msg);
        }

        logs.MakeALog(msg, "ERROR");
    }

    /**
     * Method to display a critical message in the console.
     * This method prints the message in magenta color if showLogs is enabled,
     * and always logs it to file.
     *
     * @param msg The message to log
     */
    public void CRITICAL(String msg) {
        if (showLogs) {
            // Magenta
            String CRITICAL = "\u001B[35m[CRITICAL] \u001B[0m";
            System.out.println(CRITICAL + msg);
        }

        logs.MakeALog(msg, "CRITICAL");
    }
}