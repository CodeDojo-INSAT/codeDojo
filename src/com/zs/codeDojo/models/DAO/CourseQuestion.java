package com.zs.codeDojo.models.DAO;

public class CourseQuestion {
    
    private String level;
    private String title;
    private String questionDescription;
    private String questionCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getlevel() {
        return level;
    }
    
    public void setlevel(String level) {
        this.level = level;
    }
    
    public String getQuestionDescription() {
        return questionDescription;
    }
    
    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }
    
    
    public CourseQuestion(String level,String title,String questionDescription,String questionCode){
        
        this.level = level;
        this.title = title;
        this.questionDescription = questionDescription;
        this.questionCode = questionCode;

    }
    
    public CourseQuestion(){
    
    }
    
}
