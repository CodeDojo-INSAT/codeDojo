package com.zs.codeDojo.models.DAO;

public class Question {
    private String title;
    private String description;

    public Question(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Question(){

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


}
