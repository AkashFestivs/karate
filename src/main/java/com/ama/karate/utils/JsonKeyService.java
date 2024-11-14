package com.ama.karate.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonKeyService {

    public static String getJsonKey(String jsonString, String key) {
        JsonObject jsonObj = JsonParser.parseString(jsonString).getAsJsonObject();
        return jsonObj.get("phoneNo").getAsString();
    }
    
}
