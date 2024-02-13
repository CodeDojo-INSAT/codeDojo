package com.zs.codeDojo.models.DAO;

import org.json.JSONObject;

public class OptionObject {
        private final String QuizID;
        private final String QuestionID;
        private final String OptionID;
        private final String OptionText;

        private final boolean isAnswer;

    public OptionObject(String QuizID, String QuestionID, String OptionID, String OptionText, boolean isAnswer){
        this.QuizID = QuizID;
        this.QuestionID = QuestionID;
        this.OptionID = OptionID;
        this.OptionText = OptionText;
        this.isAnswer = isAnswer;
    }
    public String getQuizID() {
        return QuizID;
    }

    public String getQuestionID() {
        return QuestionID;
    }

    public String getOptionID() {
        return OptionID;
    }

    public String getOptionText() {
        return OptionText;
    }
    public boolean isAnswer(){
        return isAnswer;
    }

    public String toJSONMinmal(){
        JSONObject obj = new JSONObject();
        obj.put("QuizID", getQuizID());
        obj.put("QuestionID", getQuestionID());
        obj.put("OptionID", getOptionID());
        obj.put("OptionText", getOptionText());
        obj.put("isAnswer", isAnswer());

        return obj.toString();
    }
}
