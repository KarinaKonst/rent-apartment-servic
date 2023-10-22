package com.example.rentapartment.map;

import java.util.HashMap;
import java.util.Map;

public class CityMap {
    private static final Map<String, String> cityMap = new HashMap<>();

    static {
        cityMap.put("Penza", "Пенза");
        cityMap.put("Saint Petersburg", "Санкт-Петербург");
        cityMap.put("Moscow", "Москва");
        cityMap.put("Saransk", "Саранск");
        cityMap.put("Saratov", "Саратов");
        cityMap.put("Samara", "Самара");
        cityMap.put("Kazan", "Казань");
        cityMap.put("Krasnodar", "Краснодар");
        cityMap.put("Sochi", "Сочи");


    }

    public static String getDescription(String conditionCode) {
        return cityMap.getOrDefault(conditionCode, "Unknown");
    }
}
