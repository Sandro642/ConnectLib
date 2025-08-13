package fr.sandro642.github.misc;

import fr.sandro642.github.annotations.AnnotHandler;

/**
 * Logger is a utility class for logging messages in the ConnectLib library.
 * It provides methods to log messages with different severity levels:
 * INFO, WARN, ERROR, and CRITICAL.
 *
 * The logs are only displayed in console when @DisplayLogs annotation is used.
 *
 * @author Sandro642
 * @version 1.0
 */
public class Logger {

	private Logs logs = new Logs();

	/**
	 * Method to display an informational message in the console.
	 * This method prints the message in green color and logs it only if logs are enabled via @DisplayLogs.
	 * @param msg
	 */
	public void INFO(String msg) {
		// Always save to logs
		logs.MakeALog(msg, "INFO");

		// Only display in console if @DisplayLogs is active
		if (AnnotHandler.areLogsEnabled()) {
			// Green
			String INFO = "\u001B[32m[INFO] \u001B[0m";
			System.out.println(INFO + msg);
		}
	}

	/**
	 * Method to display a warning message in the console.
	 * This method prints the message in yellow color and logs it only if logs are enabled via @DisplayLogs.
	 * @param msg
	 */
	public void WARN(String msg) {
		// Always save to logs
		logs.MakeALog(msg, "WARN");

		// Only display in console if @DisplayLogs is active
		if (AnnotHandler.areLogsEnabled()) {
			// Yellow
			String WARN = "\u001B[33m[WARN] \u001B[0m";
			System.out.println(WARN + msg);
		}
	}

	/**
	 * Method to display an error message in the console.
	 * This method prints the message in red color and logs it only if logs are enabled via @DisplayLogs.
	 * @param msg
	 */
	public void ERROR(String msg) {
		// Always save to logs
		logs.MakeALog(msg, "ERROR");

		// Only display in console if @DisplayLogs is active
		if (AnnotHandler.areLogsEnabled()) {
			// Red
			String ERROR = "\u001B[31m[ERROR] \u001B[0m";
			System.out.println(ERROR + msg);
		}
	}

	/**
	 * Method to display a critical message in the console.
	 * This method prints the message in magenta color and logs it only if logs are enabled via @DisplayLogs.
	 * @param msg
	 */
	public void CRITICAL(String msg) {
		// Always save to logs
		logs.MakeALog(msg, "CRITICAL");

		// Only display in console if @DisplayLogs is active
		if (AnnotHandler.areLogsEnabled()) {
			// Magenta
			String CRITICAL = "\u001B[35m[CRITICAL] \u001B[0m";
			System.out.println(CRITICAL + msg);
		}
	}
}