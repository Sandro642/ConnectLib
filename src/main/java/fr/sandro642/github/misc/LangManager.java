package fr.sandro642.github.misc;

import fr.sandro642.github.ConnectLib;

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
 * @author Sandro642
 * @version 1.0
 */
public class LangManager {

    private final Map<String, Map<String, String>> messages;
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("\\[(.+)]");
    private static final Pattern MESSAGE_PATTERN = Pattern.compile("(.+?):\\s*(.+)");
    private boolean loadedSuccessfully;
    private String loadError;

    /**
     * LangManager constructor.
     * Automatically loads the language file configured in ConnectLib.
     */
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
        } catch (IllegalArgumentException e) {
            this.loadError = e.getMessage();
            System.err.println("Error loading language file: " + e.getMessage());
        } catch (Exception e) {
            this.loadError = e.getMessage();
            System.err.println("Error loading language file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Loads the language file and parses its content.
     *
     * @param langFilePath path to the language file retrieved from ConnectLib
     */
    private void loadLangFile(String langFilePath) {
        try {
            // Remove leading / if present for getResourceAsStream
            String resourcePath = langFilePath.startsWith("/") ? langFilePath.substring(1) : langFilePath;

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

            if (inputStream == null) {
                throw new RuntimeException("Language file not found: " + langFilePath + " (searched: " + resourcePath + ")");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String currentCategory = null;
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    // Ignore empty lines and comments
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }

                    // Detect a category
                    Matcher categoryMatcher = CATEGORY_PATTERN.matcher(line);
                    if (categoryMatcher.matches()) {
                        currentCategory = categoryMatcher.group(1);
                        messages.putIfAbsent(currentCategory, new HashMap<>());
                        continue;
                    }

                    // Detect a message
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
     * Retrieves a translated message with argument replacement (varargs version).
     * Arguments are passed as pairs: placeholder_name, value
     *
     * @param category The message category (e.g., "connectlib.class")
     * @param messagePath The message path (e.g., "initialise.catcherror")
     * @param arguments Arguments to replace in the message (e.g., "exception", "NullPointerException")
     * @return The formatted message with replaced arguments
     *
     * @example
     * <pre>
     * getMessage("connectlib.class", "initialise.catcherror", "exception", "NullPointerException")
     * </pre>
     */
    public String getMessage(String category, String messagePath, String... arguments) {
        if (arguments == null || arguments.length == 0) {
            return getMessage(category, messagePath, (Map<String, String>) null);
        }

        // Convert varargs to Map
        Map<String, String> argsMap = new HashMap<>();
        for (int i = 0; i < arguments.length - 1; i += 2) {
            argsMap.put(arguments[i], arguments[i + 1]);
        }

        return getMessage(category, messagePath, argsMap);
    }

    /**
     * Retrieves a translated message with argument replacement (Map version).
     *
     * @param category The message category (e.g., "connectlib.class")
     * @param messagePath The message path (e.g., "initialise.catcherror")
     * @param arguments Map containing key-value pairs to replace placeholders
     * @return The formatted message with replaced arguments
     *
     * @example
     * <pre>
     * Map&lt;String, String&gt; args = new HashMap&lt;&gt;();
     * args.put("exception", "NullPointerException");
     * getMessage("connectlib.class", "initialise.catcherror", args)
     *
     * // Or with Map.of() (Java 9+)
     * getMessage("connectlib.class", "initialise.catcherror", Map.of("exception", "NullPointerException"))
     * </pre>
     */
    public String getMessage(String category, String messagePath, Map<String, String> arguments) {
        if (!loadedSuccessfully) {
            return "Error: language file not loaded (" + (loadError != null ? loadError : "unknown reason") + ")";
        }

        Map<String, String> categoryMessages = messages.get(category);

        if (categoryMessages == null) {
            return "Message not found: category [" + category + "] does not exist";
        }

        String message = categoryMessages.get(messagePath);

        if (message == null) {
            return "Message not found: [" + category + "] " + messagePath;
        }

        // Replace arguments
        if (arguments != null && !arguments.isEmpty()) {
            for (Map.Entry<String, String> entry : arguments.entrySet()) {
                String placeholder = "%" + entry.getKey() + "%";
                String value = entry.getValue();
                message = message.replace(placeholder, value);
            }
        }

        return message;
    }

    /**
     * Checks if the language file was loaded successfully.
     *
     * @return true if loading succeeded, false otherwise
     */
    public boolean isLoadedSuccessfully() {
        return loadedSuccessfully;
    }

    /**
     * Returns the loading error if there is one.
     *
     * @return The error message or null if no error
     */
    public String getLoadError() {
        return loadError;
    }

    /**
     * Checks if a category exists in the language file.
     *
     * @param category The category name to check
     * @return true if the category exists, false otherwise
     */
    public boolean hasCategory(String category) {
        return messages.containsKey(category);
    }

    /**
     * Checks if a message exists in a category.
     *
     * @param category The category name
     * @param messagePath The message path in the category
     * @return true if the message exists, false otherwise
     */
    public boolean hasMessage(String category, String messagePath) {
        return messages.containsKey(category) &&
                messages.get(category).containsKey(messagePath);
    }

    /**
     * Retrieves all available categories.
     *
     * @return A Set containing all category names
     */
    public java.util.Set<String> getCategories() {
        return messages.keySet();
    }

    /**
     * Retrieves all messages from a category.
     *
     * @param category The category name
     * @return A Map containing all messages in the category, or null if the category doesn't exist
     */
    public Map<String, String> getMessagesInCategory(String category) {
        return messages.get(category);
    }
}