package fr.sandro642.github.utils;

/**
 * Logger est une classe utilitaire pour afficher des messages de log dans la
 * console.
 * Elle permet d'afficher des messages d'information, d'avertissement, d'erreur
 * et critique
 * avec des couleurs différentes pour une meilleure lisibilité.
 *
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class Logger {

	private Logs logs = new Logs();

	/**
	 * Méthode pour afficher un message d'information dans la console.
	 * 
	 * @param msg le message à afficher
	 */
	public void INFO(String msg) {
		// Green
		String INFO = "\u001B[32m[INFO] \u001B[0m";
		System.out.println(INFO + msg);

		logs.MakeALog(msg, "INFO");
	}

	/**
	 * Méthode pour afficher un message d'avertissement dans la console.
	 * 
	 * @param msg le message à afficher
	 */
	public void WARN(String msg) {
		// Yellow
		String WARN = "\u001B[33m[WARN] \u001B[0m";
		System.out.println(WARN + msg);

		logs.MakeALog(msg, "WARN");
	}

	/**
	 * Méthode pour afficher un message d'erreur dans la console.
	 * 
	 * @param msg le message à afficher
	 */
	public void ERROR(String msg) {
		// Red
		String ERROR = "\u001B[31m[ERROR] \u001B[0m";
		System.out.println(ERROR + msg);

		logs.MakeALog(msg, "ERROR");
	}

	/**
	 * Méthode pour afficher un message critique dans la console.
	 * 
	 * @param msg le message à afficher
	 */
	public void CRITICAL(String msg) {
		// Magenta
		String CRITICAL = "\u001B[35m[CRITICAL] \u001B[0m";
		System.out.println(CRITICAL + msg);

		logs.MakeALog(msg, "CRITICAL");
	}
}
