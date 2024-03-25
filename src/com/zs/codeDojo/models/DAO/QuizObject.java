package com.zs.codeDojo.models.DAO;

import org.json.JSONObject;

public class QuizObject {
    private final String quizID;
    private final String quizType;

    private final String quizName;

    private String createdDate;
    private String lastUpdated;

    private int numQuestions;

    private JSONObject submissionInfo = null;

    private String toJSONType = "DEF";

    public QuizObject(String quizID, String quizName, String quizType, String createdDate, String lastUpdated, int numQuestions, JSONObject submissionInfo) {
        this.quizID = quizID;
        this.quizName = quizName;
        this.quizType = quizType;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
        this.numQuestions = numQuestions;
        this.submissionInfo = submissionInfo;
        this.toJSONType = "ADV";
    }

    public QuizObject(String quizID, String quizName, String quizType, String createdDate, String lastUpdated, int numQuestions) {
        this.quizID = quizID;
        this.quizName = quizName;
        this.quizType = quizType;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
        this.numQuestions = numQuestions;
    }

    public QuizObject(String quizID, String quizName, String quizType) {
        this.quizID = quizID;
        this.quizName = quizName;
        this.quizType = quizType;
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("quizID", this.quizID);
        obj.put("quizName", this.quizName);
        obj.put("quizType", this.quizType);
        obj.put("numQuestions", this.numQuestions);
        obj.put("createdOn", this.createdDate);
        obj.put("lastUpdated", this.lastUpdated);
        if (toJSONType == "ADV") {
            obj.put("submissionInfo", submissionInfo);
        }

        return obj;

    }

    public JSONObject toJsonMinimal(){
        JSONObject obj = new JSONObject();
        obj.put("quizID", this.quizID);
        obj.put("quizName", this.quizName);
        obj.put("quizType", this.quizType);

        return obj;

    }
    public String getQuizID() {
        return quizID;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getQuizType() {
        return quizType;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public int getNumQuestions(){
     return numQuestions;
    }

}
