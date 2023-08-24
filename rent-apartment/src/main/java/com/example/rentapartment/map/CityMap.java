package com.example.rentapartment.map;

import java.util.HashMap;
import java.util.Map;

public class CityMap {
    private static final Map<String, String> cityMap = new HashMap<>();

    static {
     cityMap.put("Penza","Пенза");
    }

    public static String getDescription(String conditionCode) {
        return cityMap.getOrDefault(conditionCode, "Unknown");
    }
}
