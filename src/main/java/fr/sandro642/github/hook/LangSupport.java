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
    private final String PREFIX = "lang/";

    /**
     * Private singleton langType.
     * @return langTypeSingleton
     */
    private LangType langTypeSingleton;

    /**
     * Sets the LangType variable for this instance.
     * @param langType The language type to set. Must not be null.
     * @return The LangType that was set.
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

        return PREFIX + langTypeSingleton.getLang() + ".lang";
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
