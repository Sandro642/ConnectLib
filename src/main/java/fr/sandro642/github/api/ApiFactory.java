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

    /**
     * connectLib is an instance of ConnectLib that provides access to the library's configuration and utilities.
     */
    private ConnectLib connectLib = new ConnectLib();

    /**
     * rawData is a Map that holds the parsed JSON data.
     * It is populated by the parseFromRawJson method.
     */
    private Map<String, Object> rawData;

    /**
     * rawJson is a String that holds the raw JSON data.
     * It is used for logging and debugging purposes.
     */
    private String rawJson;

    /**
     * statusCode is an integer that holds the HTTP status code of the API response.
     * It can be set using the setStatusCode method.
     */
    private int statusCode;

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
            connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "parsefromrawjson.error", Map.of("json", rawJson, "exception", e.getMessage())));
        }
    }

    /**
     * Method to retrieve the statusCode from response.
     */
    protected void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
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
                connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "general.mustbe"));
                return null;
            }

            return rawData.get(type.toString());
        } catch (Exception e) {
            connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "getdata.error", Map.of("type", type.toString(), "exception", e.getMessage())));
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
                connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "general.mustbe"));
                return null;
            }

            Object nested = rawData.get(type.toString());
            if (nested instanceof Map) {
                Map<?, ?> nestedMap = (Map<?, ?>) nested;
                return nestedMap.get(value.toString());
            }
        } catch (Exception e) {
            connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "getspecdata.error", Map.of("type", type.toString(), "value", value.toString(), "exception", e.getMessage())));
        }
        return null;
    }

    /**
     * Method to retrieve the HTTP status code of the API response.
     *
     * @return The HTTP status code of the API response.
     */
    public int getStatusCode() {
        try {
            if (rawData == null) {
                connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "general.mustbe"));
                return -1;
            }

            return statusCode;
        } catch (Exception e) {
            connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "getstatus.error", "exception", e.getMessage()));
        }
        return -1;
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
                connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "general.mustbe"));
                return null;
            }
            return rawData;
        } catch (Exception e) {
            connectLib.Logger().ERROR(connectLib.LangManager().getMessage(CategoriesType.APIFACTORY_CLASS, "display.error", "exception", e.getMessage()));
        }
        return null;
    }

}