package fr.sandro642.github.enums.lang;

/**
 * CategoriesType is an enumeration representing different categories used in the ConnectLib library.
 *
 * @author Sandro642
 * @version 1.0
 */

public enum CategoriesType {

    /**
     * Category for ConnectLib class files.
     */
    CONNECTLIB_CLASS("connectlib.class"),
    ANNOTATION_PACKAGE("annotation.package"),
    YAMLUTILS_CLASS("yamlutils.class")
    ;

    /**
     * Category for Enums files.
     */
    private final String category;

    /**
     * Constructor to initialize the category.
     * @param category
     */
    CategoriesType(String category) {
        this.category = category;
    }

    /**
     * Get the category.
     * @return the category string
     */
    public String getCategory() {
        return category;
    }
}
