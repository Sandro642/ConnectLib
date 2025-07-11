package fr.sandro642.github.api;

import java.util.Map;

/**
 * ApiResponse est une classe générique qui représente la réponse d'une API.
 * Elle contient des informations sur l'état de la réponse, un message, un code de statut et des données spécifiques.
 * @param <T> Le type de données contenues dans la réponse.
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class ApiResponse<T> {

    /**
     * Variables d'instance
     * err : Indique s'il y a une erreur dans la réponse.
     * Msg : Message d'erreur ou de succès.
     * Code : Code de statut de la réponse (par exemple, HTTP status code).
     * Data : Données spécifiques de la réponse, stockées dans une Map.
     */
    private boolean err;
    private String msg;
    private int code;
    private Map<String, Object> data;

    // Constructeurs
    public ApiResponse() {
    }

    /**
     * Constructeur pour initialiser la réponse d'API avec des valeurs spécifiques.
     * @param err
     * @param msg
     * @param code
     * @param data
     */
    public ApiResponse(boolean err, String msg, int code, Map<String, Object> data) {
        this.err = err;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    /**
     * Vérifie s'il y a une erreur dans la réponse.
     * @return
     */
    public boolean isErr() {
        return err;
    }

    /**
     * Définit si la réponse contient une erreur.
     * @param err
     */
    public void setErr(boolean err) {
        this.err = err;
    }

    /**
     * Récupère le message de la réponse.
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Définit le message de la réponse.
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Récupère le code de statut de la réponse.
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * Définit le code de statut de la réponse.
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Récupère les données de la réponse.
     * @return
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Définit les données de la réponse.
     * @param data
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Récupère une donnée spécifique à partir de la Map 'data' en utilisant une clé.
     * @deprecated En raison de la nature générique de cette méthode créant des erreurs, il est recommandé d'utiliser les méthodes spécifiques pour chaque type de données. Cette méthode est obsolète et peut être supprimée dans les futures versions.
     * @see #getSpecDataBoolean(String key)
     * @see #getSpecDataDouble(String key)
     * @see #getSpecDataString(String key)
     * @see #getSpecDataInteger (String key)
     * @param key La clé pour accéder à la donnée.
     * @return La valeur associée à la clé dans la Map 'data'.
     */
    @Deprecated
    public Object getSpecData(String key) {
        if (data == null) {
            throw new RuntimeException("Le champ 'data' est null.");
        }
        return data.get(key);
    }

    /**
     * Récupère une donnée spécifique à partir de la Map 'data' en utilisant une clé et la convertit en type spécifié.
     * @param key La clé pour accéder à la donnée.
     * @return La valeur associée à la clé dans la Map 'data', convertie au type spécifié.
     */
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

    /**
     * Récupère une donnée spécifique à partir de la Map 'data' en utilisant une clé et la convertit en type Double.
     * @param key La clé pour accéder à la donnée.
     * @return La valeur associée à la clé dans la Map 'data', convertie en Double.
     */
    public Double getSpecDataDouble(String key) {
        if (data == null) {
            throw new RuntimeException("Le champ 'data' est null.");
        }
        Object value = data.get(key);
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            throw new RuntimeException("La valeur de la clé '" + key + "' n'est pas un double.");
        }
    }

    /**
     * Récupère une donnée spécifique à partir de la Map 'data' en utilisant une clé et la convertit en type String.
     * @param key La clé pour accéder à la donnée.
     * @return La valeur associée à la clé dans la Map 'data', convertie en String.
     */
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

    /**
     * Récupère une donnée spécifique à partir de la Map 'data' en utilisant une clé et la convertit en type Integer.
     * @param key La clé pour accéder à la donnée.
     * @return La valeur associée à la clé dans la Map 'data', convertie en Integer.
     */
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

    /**
     * Récupère une donnée spécifique à partir de la Map 'data' en utilisant une clé et la convertit en type Long.
     * @return La valeur associée à la clé dans la Map 'data', convertie en Long.
     */
    public String display() {
        return "ApiResponse{" +
                "err=" + err +
                ", msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}