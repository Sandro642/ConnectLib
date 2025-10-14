package fr.sandro642.github.enums;

/**
 * LangType is an enumeration representing different language codes used in the ConnectLib library.
 *
 * @author Sandro642
 * @version 1.0
 */

public enum LangType {
    ENGLISH("EN_US"),
    FRENCH("FR_EU"),
    SPANISH("ES_EU"),
    GERMAN("DE_EU"),
    ITALIAN("IT_EU"),
    PORTUGUESE("PT_EU"),
    RUSSIAN("RU_RU");

    /**
     * Language code.
     */
    private final String lang;

    /**
     * Constructor to initialize the language code.
     *
     * @param lang
     */
    LangType(String lang) {
        this.lang = lang;
    }

    /**
     * Get the language code.
     *
     * @return the language code string
     */
    public String getLang() {
        return lang;
    }
}
