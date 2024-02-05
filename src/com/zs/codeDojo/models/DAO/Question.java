package com.zs.codeDojo.models.DAO;

public class Question {
    private final String title;
    private final String description;

    public Question(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
