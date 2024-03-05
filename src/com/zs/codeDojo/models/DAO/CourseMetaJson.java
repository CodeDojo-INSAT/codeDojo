package com.zs.codeDojo.models.DAO;

// import org.json.JSONArray;
import org.json.JSONObject;

public class CourseMetaJson {
    
    private String levelId;
    private String title;
    
    @Override
    public String toString() {
        // JSONArray jsonArray = new JSONArray();
        JSONObject json = new JSONObject();

        json.put("LevelID", levelId);
        json.put("Title", title);
       
        // jsonArray.put(json);
        return json.toString();
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CourseMetaJson(String levelId, String title) {
        this.levelId = levelId;
        this.title = title;
    }
}
