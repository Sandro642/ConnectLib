package fr.sandro642.github.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sandro642.github.ConnectLib;
import fr.sandro642.github.enums.lang.CategoriesType;

import java.util.Map;

/**
 * ApiFactory is a class that provides methods to parse raw JSON data and retrieve specific data from it.
 * It uses Jackson's ObjectMapper to convert JSON strings into Java objects.
 * This class is designed to handle API responses and allows for easy access to the parsed data.
 *
 * @author Sandro642
 * @version 1.0
 */

public class ApiFactory {
    private Map<String, Object> rawData;
    private String rawJson;

    /**
     * Default constructor for ApiFactory.
     * Initializes the rawData and rawJson fields to null.
     */
    protected void parseFromRawJson(String rawJson) {
        this.rawJson = rawJson;
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.rawData = mapper.readValue(rawJson, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            ConnectLib.Logger().ERROR(ConnectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "parsefromrawjson.error", Map.of("json", rawJson, "exception", e.getMessage())));
        }
    }

    /**
     * Method to retrieve data from the rawData map based on the provided type.
     * The type is converted to lowercase to ensure case-insensitive matching.
     *
     * @param type The type of data to retrieve, which is expected to be a string representation of the key.
     * @return The data associated with the specified type, or null if not found.
     */
    public <O> Object getData(O type) {
        try {
            if (rawData == null) {
                ConnectLib.Logger().ERROR(ConnectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "general.mustbe"));
                return null;
            }

            return rawData.get(type.toString().toLowerCase());
        } catch (Exception e) {
            ConnectLib.Logger().ERROR(ConnectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "getdata.error", Map.of("type", type.toString(), "exception", e.getMessage())));
        }
        return null;
    }

    /**
     * Method to retrieve nested data from the rawData map based on the provided type and value.
     * The type and value are converted to lowercase to ensure case-insensitive matching.
     *
     * @param type  The type of data to retrieve, which is expected to be a string representation of the key.
     * @param value The specific value within the nested map to retrieve.
     * @return The nested data associated with the specified type and value, or null if not found.
     */
    public <O, K> Object getSpecData(O type, K value) {
        try {
            if (rawData == null) {
                ConnectLib.Logger().ERROR(ConnectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "general.mustbe"));
                return null;
            }

            Object nested = rawData.get(type.toString().toLowerCase());
            if (nested instanceof Map) {
                Map<?, ?> nestedMap = (Map<?, ?>) nested;
                return nestedMap.get(value.toString().toLowerCase());
            }
        } catch (Exception e) {
            ConnectLib.Logger().ERROR(ConnectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "getspecdata.error", Map.of("type", type.toString(), "value", value.toString(), "exception", e.getMessage())));
        }
        return null;
    }

    /**
     * Method to display the raw data stored in the ApiFactory.
     * This method returns the rawData map, which contains the parsed JSON data.
     *
     * @return The rawData map containing the parsed JSON data, or null if rawData is not initialized.
     */
    public Object display() {
        try {
        if (rawData == null) {
            ConnectLib.Logger().ERROR(ConnectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "general.mustbe"));
            return null;
        }
        return rawData;
        } catch (Exception e) {
            ConnectLib.Logger().ERROR(ConnectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "display.error", "exception", e.getMessage()));
        }
        return null;
    }

}