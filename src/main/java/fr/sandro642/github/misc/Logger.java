package fr.sandro642.github.misc;

/**
 * Logger is a utility class for logging messages in the ConnectLib library.
 * It provides methods to log messages with different severity levels:
 * INFO, WARN, ERROR, and CRITICAL.
 *
 * @author Sandro642
 * @version 1.0
 */

public class Logger {

	private Logs logs = new Logs();

	/**
	 * Method to display an informational message in the console.
	 * This method prints the message in green color and logs it.
	 * @param msg
	 */
	public void INFO(String msg) {
		// Green
		String INFO = "\u001B[32m[INFO] \u001B[0m";
		System.out.println(INFO + msg);

		logs.MakeALog(msg, "INFO");
	}

	/**
	 * Method to display a warning message in the console.
	 * This method prints the message in yellow color and logs it.
	 * @param msg
	 */
	public void WARN(String msg) {
		// Yellow
		String WARN = "\u001B[33m[WARN] \u001B[0m";
		System.out.println(WARN + msg);

		logs.MakeALog(msg, "WARN");
	}

	/**
	 * Method to display an error message in the console.
	 * This method prints the message in red color and logs it.
	 * @param msg
	 */
	public void ERROR(String msg) {
		// Red
		String ERROR = "\u001B[31m[ERROR] \u001B[0m";
		System.out.println(ERROR + msg);

		logs.MakeALog(msg, "ERROR");
	}

	/**
	 * Method to display a critical message in the console.
	 * This method prints the message in magenta color and logs it.
	 * @param msg
	 */
	public void CRITICAL(String msg) {
		// Magenta
		String CRITICAL = "\u001B[35m[CRITICAL] \u001B[0m";
		System.out.println(CRITICAL + msg);

		logs.MakeALog(msg, "CRITICAL");
	}
}
