package fr.sandro642.github.api;

import java.util.Map;

public class ApiResponse<T> {
    private boolean err;
    private String msg;
    private int code;
    private Map<String, Object> data;

    // Getters and setters

    public boolean isErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Object getSpecData(String key) {
        if (data == null) {
            throw new RuntimeException("Le champ 'data' est null.");
        }
        return data.get(key);
    }

    public boolean getSpecDataBoolean(String key) {
        if (data == null) {
            throw new RuntimeException("Le champ 'data' est null.");
        }
        Object value = data.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            throw new RuntimeException("La valeur de la clé '" + key + "' n'est pas un booléen.");
        }
    }
}