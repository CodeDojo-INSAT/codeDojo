package com.zs.codeDojo.models.DAO;

import org.json.JSONArray;
import org.json.JSONObject;

// import java.util.Arrays;

public class QuestionObject {
    private String quizID;
    private String questionID;

    private String questionText;

    private String questionType;

    private JSONArray options;

    public QuestionObject(String quizID, String questionID, String questionText, String questionType, JSONArray options){
        this.quizID = quizID;
        this.questionID = questionID;
        this.questionText = questionText;
        this.questionType = questionType;
        this.options = options;
    }

    public QuestionObject(String quizID, String questionID, String questionText, String questionType){
        this.quizID = quizID;
        this.questionID = questionID;
        this.questionText = questionText;
        this.questionType = questionType;

    }


    public String getQuizID() {
        return quizID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public JSONArray getOptions() {
        return options;
    }

    public JSONObject toJSON() {

        JSONObject obj = new JSONObject();

        obj.put("quizID", quizID);
        obj.put("questionID", questionID);
        obj.put("questionText", questionText);
        obj.put("questionType", questionType);
        if (questionType.equals("MCQ")){
            obj.append("options", options);
        }
        return obj;
    }
}
