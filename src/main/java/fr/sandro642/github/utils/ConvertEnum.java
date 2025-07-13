package fr.sandro642.github.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ConvertEnum est une classe utilitaire pour convertir des énumérations en routes.
 * Elle permet de récupérer les routes définies dans les énumérations qui implémentent l'interface RouteImport.
 * @author Sandro642
 * @version 1.0
 * @since 1.0
 */

public class ConvertEnum {

    /**
     * Interface RouteImport pour les énumérations qui définissent une route.
     * Les énumérations doivent implémenter cette interface pour être converties en routes.
     */
    public interface RouteImport {
        String route();
    }

    /**
     * Convertit une énumération en une map de routes.
     * Seules les énumérations qui implémentent l'interface RouteImport seront converties.
     *
     * @param enumClass la classe de l'énumération à convertir
     * @return une map contenant les énumérations et leurs routes associées
     */
    public static Map<Enum<?>, String> convertRouteImport(Class<? extends Enum<?>> enumClass) {
        Enum<?>[] enums = enumClass.getEnumConstants();
        HashMap<Enum<?>, String> map = new HashMap<>();
        for (Enum<?> e : enums) {
            if (e instanceof RouteImport routeImport) {
                map.put((Enum<?>) routeImport, routeImport.route());
            }
        }
        return map;
    }


    public interface SchemaImport {
        String schema();
    }

    /**
     * Convertit une énumération en une map de schémas.
     * Seules les énumérations qui implémentent l'interface SchemaImport seront converties.
     *
     * @param enumClass la classe de l'énumération à convertir
     * @return une map contenant les énumérations et leurs schémas associés
     */
    public static Map<Enum<?>, String> convertSchemaImport(Class<? extends Enum<?>> enumClass) {
        Enum<?>[] enums = enumClass.getEnumConstants();
        HashMap<Enum<?>, String> map = new HashMap<>();
        for (Enum<?> e : enums) {
            if (e instanceof SchemaImport schemaImport) {
                map.put((Enum<?>) schemaImport, schemaImport.schema());
            }
        }
        return map;
    }
}
