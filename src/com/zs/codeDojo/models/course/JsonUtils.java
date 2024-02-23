package com.zs.codeDojo.models.course;

import com.google.gson.Gson;

public class JsonUtils {
    private static final Gson gson = new Gson();

    // Method to convert an object to JSON string
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }
}
