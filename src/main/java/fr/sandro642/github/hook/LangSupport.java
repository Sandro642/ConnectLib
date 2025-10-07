package fr.sandro642.github.hook;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.enums.LangType;

/**
 * LangSupport is a placeholder class for future language support implementations.
 * Currently, it does not contain any methods or properties.
 *
 * @author Sandro642
 * @version 1.0
 */

public class LangSupport {

    /**
     * Instance of LangSupport.
     */
    private static LangSupport instance;

    /**
     * Prefix for language files.
     */
    private final String PREFIX = "lang";

    /**
     * Paths for different languages.
     * FRENCH_PATH, ENGLISH_PATH, SPANISH_PATH, GERMAN_PATH, ITALIAN_PATH, PORTUGUESE_PATH, RUSSIAN_PATH
     */
    private final String FRENCH_PATH = ConnectLib.HookManager().BASE_PATH() + "/" + PREFIX + "/";

    private final String ENGLISH_PATH = ConnectLib.HookManager().BASE_PATH() + "/" + PREFIX + "/";

    private final String SPANISH_PATH = ConnectLib.HookManager().BASE_PATH() + "/" + PREFIX + "/";

    private final String GERMAN_PATH = ConnectLib.HookManager().BASE_PATH() + "/" + PREFIX + "/";

    private final String ITALIAN_PATH = ConnectLib.HookManager().BASE_PATH() + "/" + PREFIX + "/";

    private final String PORTUGUESE_PATH = ConnectLib.HookManager().BASE_PATH() + "/" + PREFIX + "/";

    private final String RUSSIAN_PATH = ConnectLib.HookManager().BASE_PATH() + "/" + PREFIX + "/";

    /**
     * Private singleton langType.
     * @return langTypeSingleton
     */
    private LangType langTypeSingleton;

    /**
     * Method to set the LangType variable.
     * @param langType
     * @return
     */
    public LangType setLangTypeVariable(LangType langType) {
        if (langType == null) {
            throw new IllegalArgumentException("LangType cannot be null");
        }
        this.langTypeSingleton = langType;
        return this.langTypeSingleton;
    }

    /**
     * Method to get the path of the language file based on the LangType.
     * @return The path of the language file.
     * @throws IllegalArgumentException If the LangType is null or unsupported.
     */
    public String getPathFile() {
        if (langTypeSingleton == null) {
            throw new IllegalArgumentException("LangType cannot be null");
        }

        switch (langTypeSingleton) {
            case FRENCH:
                return FRENCH_PATH + langTypeSingleton.getLang() + ".lang";
            case ENGLISH:
                return ENGLISH_PATH + langTypeSingleton.getLang() + ".lang";
            case SPANISH:
                return SPANISH_PATH + langTypeSingleton.getLang() + ".lang";
            case GERMAN:
                return GERMAN_PATH + langTypeSingleton.getLang() + ".lang";
            case ITALIAN:
                return ITALIAN_PATH + langTypeSingleton.getLang() + ".lang";
            case PORTUGUESE:
                return PORTUGUESE_PATH + langTypeSingleton.getLang() + ".lang";
            case RUSSIAN:
                return RUSSIAN_PATH + langTypeSingleton.getLang() + ".lang";

            default:
                ConnectLib.Logger().CRITICAL("Unsupported LangType: " + langTypeSingleton);
                throw new IllegalArgumentException("Unsupported LangType: " + langTypeSingleton);
        }
    }

    /**
     * Public Getter of LangSupport instance.
     * This method returns the singleton instance of LangSupport.
     * If the instance is null, it creates a new instance.
     * @return instance
     */
    public static LangSupport getInstance() {
        if (instance == null) {
            instance = new LangSupport();
        }
        return instance;
    }
}
