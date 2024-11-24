package com.ama.karate.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonKeyService {

    public static String getJsonKey(String jsonString, String key) {
        JsonObject jsonObj = JsonParser.parseString(jsonString).getAsJsonObject();
        return jsonObj.get(key).getAsString();
    }

    public static List<Map<String, String>> getJsonKeys(String jsonString, String key1, String key2) {
        JsonArray jsonArray = JsonParser.parseString(jsonString).getAsJsonArray();
        List<Map<String, String>> extractedKeysList = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            JsonObject jsonObj = element.getAsJsonObject();
            Map<String, String> extractedKeys = new HashMap<>();

            if (jsonObj.has(key1)) {
                extractedKeys.put(key1, jsonObj.get(key1).getAsString());
            }
            if (jsonObj.has(key2)) {
                extractedKeys.put(key2, jsonObj.get(key2).getAsString());
            }

            extractedKeysList.add(extractedKeys);
        }
        return extractedKeysList;
    }
    
}
