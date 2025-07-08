package fr.sandro642.github.utils;

public class Logger {
    /**
     * Logger est une classe utilitaire pour afficher des messages de log dans la console.
     * Elle utilise des codes ANSI pour colorer les messages en fonction de leur niveau de gravité.
     * @author Sandro642
     * @version 1.0
     */
    private String INFO = "\u001B[32m[INFO] \u001B[0m";     // Green
    private String WARN = "\u001B[33m[WARN] \u001B[0m";     // Yellow
    private String ERROR = "\u001B[31m[ERROR] \u001B[0m";   // Red
    private String CRITICAL = "\u001B[35m[CRITICAL] \u001B[0m"; // Magenta

    /**
     * Méthode pour afficher un message d'information dans la console.
     * @param msg
     */
    public void INFO(String msg) {
        System.out.println(INFO + msg);
    }

    /**
     * Méthode pour afficher un message d'avertissement dans la console.
     * @param msg
     */
    public void WARN(String msg) {
        System.out.println(WARN + msg);
    }

    /**
     * Méthode pour afficher un message d'erreur dans la console.
     * @param msg
     */
    public void ERROR(String msg) {
        System.out.println(ERROR + msg);
    }

    /**
     * Méthode pour afficher un message critique dans la console.
     * @param msg
     */
    public void CRITICAL(String msg) {
        System.out.println(CRITICAL + msg);
    }
}