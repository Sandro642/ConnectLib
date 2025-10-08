package fr.sandro642.github.misc;

import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.enums.lang.CategoriesType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LangManager handles loading and retrieving language messages
 * from .lang files with placeholder support.
 *
 * This version uses only enum-based categories (CategoriesType).
 *
 *  WARNING : THE ONLY FILE YOU CAN'T ADD IN LANG FILE, ENGLISH DEFAULT LANG.
 *
 * @author Sandro642
 * @version 2.0
 */
public class LangManager {

    private final Map<CategoriesType, Map<String, String>> messages;
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("\\[(.+)]");
    private static final Pattern MESSAGE_PATTERN = Pattern.compile("(.+?):\\s*(.+)");
    private boolean loadedSuccessfully;
    private String loadError;

    public LangManager() {
        this.messages = new HashMap<>();
        this.loadedSuccessfully = false;
        this.loadError = null;

        try {
            String langFilePath = ConnectLib.LangSupport().getPathFile();

            if (langFilePath == null) {
                this.loadError = "LangType has not been defined. Call LangSupport().setLangTypeVariable() first.";
                System.err.println("Error loading language file: " + loadError);
                return;
            }

            loadLangFile(langFilePath);
            this.loadedSuccessfully = true;
        } catch (Exception e) {
            this.loadError = e.getMessage();
            System.err.println("Error loading language file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadLangFile(String langFilePath) {
        try {
            String resourcePath = langFilePath.startsWith("/") ? langFilePath.substring(1) : langFilePath;
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

            if (inputStream == null) {
                throw new RuntimeException("Language file not found: " + langFilePath + " (searched: " + resourcePath + ")");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                CategoriesType currentCategory = null;
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) continue;

                    Matcher categoryMatcher = CATEGORY_PATTERN.matcher(line);
                    if (categoryMatcher.matches()) {
                        String catName = categoryMatcher.group(1);
                        currentCategory = parseCategory(catName);
                        if (currentCategory != null) {
                            messages.putIfAbsent(currentCategory, new HashMap<>());
                        }
                        continue;
                    }

                    Matcher messageMatcher = MESSAGE_PATTERN.matcher(line);
                    if (messageMatcher.matches() && currentCategory != null) {
                        String key = messageMatcher.group(1).trim();
                        String value = messageMatcher.group(2).trim();
                        messages.get(currentCategory).put(key, value);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading language file", e);
        }
    }

    /**
     * Convertit un nom de catégorie du fichier .lang en CategoriesType.
     */
    private CategoriesType parseCategory(String categoryName) {
        for (CategoriesType type : CategoriesType.values()) {
            if (type.getCategory().equalsIgnoreCase(categoryName)) {
                return type;
            }
        }
        System.err.println("⚠ Unknown category in .lang file: [" + categoryName + "]");
        return null;
    }

    /**
     * Récupère un message localisé avec remplacement de variables.
     *
     * @param category La catégorie (enum)
     * @param messagePath Le chemin du message
     * @param arguments Les arguments sous forme de paires clé-valeur
     */
    public String getMessage(CategoriesType category, String messagePath, String... arguments) {
        if (arguments == null || arguments.length == 0) {
            return getMessage(category, messagePath, (Map<String, String>) null);
        }

        Map<String, String> argsMap = new HashMap<>();
        for (int i = 0; i < arguments.length - 1; i += 2) {
            argsMap.put(arguments[i], arguments[i + 1]);
        }

        return getMessage(category, messagePath, argsMap);
    }

    public String getMessage(CategoriesType category, String messagePath, Map<String, String> arguments) {
        if (!loadedSuccessfully) {
            return "Error: language file not loaded (" + (loadError != null ? loadError : "unknown reason") + ")";
        }

        Map<String, String> categoryMessages = messages.get(category);

        if (categoryMessages == null) {
            return "Message not found: category [" + category.name() + "] does not exist";
        }

        String message = categoryMessages.get(messagePath);

        if (message == null) {
            return "Message not found: [" + category.name() + "] " + messagePath;
        }

        if (arguments != null && !arguments.isEmpty()) {
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                message = message.replace("%" + entry.getKey() + "%", entry.getValue());
            }
        }

        return message;
    }

    public boolean isLoadedSuccessfully() {
        return loadedSuccessfully;
    }

    public String getLoadError() {
        return loadError;
    }

    public boolean hasCategory(CategoriesType category) {
        return messages.containsKey(category);
    }

    public boolean hasMessage(CategoriesType category, String messagePath) {
        return messages.containsKey(category) &&
                messages.get(category).containsKey(messagePath);
    }

    public java.util.Set<CategoriesType> getCategories() {
        return messages.keySet();
    }

    public Map<String, String> getMessagesInCategory(CategoriesType category) {
        return messages.get(category);
    }
}
