package com.zs.codeDojo.models.DAO;

import org.json.JSONObject;

public class Title {
    private final String[] content;

    public Title(String[] content) {
        this.content = content;
    }

    public String toString() {
        String result = "[";
        for (int i=0; i<content.length-1; i++) {
            result += "\"" + content[i] + "\", ";
        }
        result += "\"" + content[content.length-1] + "\"]";
        return result;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("titles", this.toString());

        return json.toString();
    }
}