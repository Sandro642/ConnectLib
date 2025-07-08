package fr.sandro642.github.api;

import java.util.Map;

public class ApiResponse<T> {

    private boolean err;
    private String msg;
    private int code;
    private Map<String, Object> data;

    // Constructeurs
    public ApiResponse() {}

    public ApiResponse(boolean err, String msg, int code, Map<String, Object> data) {
        this.err = err;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    // Getters et Setters
    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
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

    public String getSpecDataString(String key) {
        if (data == null) {
            throw new RuntimeException("Le champ 'data' est null.");
        }
        Object value = data.get(key);
        if (value instanceof String) {
            return (String) value;
        } else {
            throw new RuntimeException("La valeur de la clé '" + key + "' n'est pas une chaîne.");
        }
    }

    public Integer getSpecDataInteger(String key) {
        if (data == null) {
            throw new RuntimeException("Le champ 'data' est null.");
        }
        Object value = data.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else {
            throw new RuntimeException("La valeur de la clé '" + key + "' n'est pas un entier.");
        }
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "err=" + err +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
