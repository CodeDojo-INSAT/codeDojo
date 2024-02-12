package com.zs.codeDojo.models.DAO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;

public class QuizExternObject {
    private final String quizID;
    private final String quizName;

    private final String quizType;

    private final JSONArray questions;
    public QuizExternObject(JSONObject obj){
        this.quizID = UUID.randomUUID().toString();
        this.quizName = obj.getString("quizName");
        this.quizType = obj.getString("quizType");
        this.questions = obj.getJSONArray("questions");
    }

//    public QuizExternObject(String id, String name, String type, JSONArray questions){
//        this.quizID = id;
//        this.quizName = name;
//        this.quizType = type;
//        this.questions = questions;
//    }

    public String getQuizName() {
        return quizName;
    }

    public String getQuizID(){
        return quizID;
    }

    public String getQuestion(int id){
        return questions.getJSONObject(id - 1).getString("question");
    }

    private JSONObject getQuestionObj(int id){
        return questions.getJSONObject(id - 1);
    }
    public String getQuizType() {
        return quizType;
    }

    public JSONArray getQuestions(){
        return questions;
    }

    public boolean checkAnswer(int id, String answer){
        JSONObject obj = getQuestionObj(id);
        return obj.getString("answer").equals(answer);
    }
}
