package fr.sandro642.github.utils;

import java.util.HashMap;
import java.util.Map;

public class ConvertEnum {
    public interface RouteImport {
        String route();
    }

    public static Map<Enum<?>, String> convert(Class<? extends Enum<?>> enumClass) {
        Enum<?>[] enums = enumClass.getEnumConstants();
        HashMap<Enum<?>, String> map = new HashMap<>();
        for (Enum<?> e : enums) {
            if (e instanceof RouteImport routeImport) {
                map.put((Enum<?>) routeImport, routeImport.route());
            }
        }
        return map;
    }
}
