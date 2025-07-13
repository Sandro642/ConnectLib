package fr.sandro642.github.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sandro642.github.ConnectorAPI;

import java.util.Map;

public class ApiFactory {
    private Map<String, Object> rawData;
    private String rawJson;



    /**
     * Parse le JSON brut et stocke les données
     */
    protected void parseFromRawJson(String rawJson) {
        this.rawJson = rawJson;
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.rawData = mapper.readValue(rawJson, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du parsing: " + e.getMessage(), e);
        }
    }

    public <O> Object getData(O type) {
        try {
            if (rawData == null) {
                throw new IllegalStateException("Les données n'ont pas été initialisées. Veuillez appeler parseFromRawJson() d'abord.");
            }

            return rawData.get(type.toString().toLowerCase());
        } catch (Exception e) {
            ConnectorAPI.Logger().ERROR("Impossible de récupérer les données pour le type: " + type + ".");
        }
        return null;
    }

    public <O, K> Object getSpecData(O type, K value) {
        try {
            if (rawData == null) {
                throw new IllegalStateException("Les données n'ont pas été initialisées. Veuillez appeler parseFromRawJson() d'abord.");
            }

            Object nested = rawData.get(type.toString().toLowerCase());
            if (nested instanceof Map) {
                Map<?, ?> nestedMap = (Map<?, ?>) nested;
                return nestedMap.get(value.toString().toLowerCase());
            }
        } catch (Exception e) {
            ConnectorAPI.Logger().ERROR("Impossible de récupérer les données pour le type: " + type + ".");
        }
        return null;
    }


    public Object display() {
        try {
        if (rawData == null) {
            throw new IllegalStateException("Les données n'ont pas été initialisées. Veuillez appeler parseFromRawJson() d'abord.");
        }
        return rawData;
        } catch (Exception e) {
            ConnectorAPI.Logger().ERROR("Impossible de récupérer les données.");
        }
        return null;
    }

}