package com.example.rentapartment.map;

import java.util.HashMap;
import java.util.Map;

public class CityMap {
    private static final Map<String, String> cityMap = new HashMap<>();

    static {
        cityMap.put("Penza", "Пенза");
        cityMap.put("Saint Petersburg", "Санкт-Петербург");
        cityMap.put("Moscow", "Москва");

    }

    public static String getDescription(String conditionCode) {
        return cityMap.getOrDefault(conditionCode, "Unknown");
    }
}
