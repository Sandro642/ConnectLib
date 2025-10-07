package fr.sandro642.github.enums;

public enum LangType {
    ENGLISH("EN_US"),
    FRENCH("FR_EU"),
    SPANISH("ES_EU"),
    GERMAN("DE_EU"),
    ITALIAN("IT_EU"),
    PORTUGUESE("PT_EU"),
    RUSSIAN("RU_RU");

    private final String lang;

    LangType(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }
}
