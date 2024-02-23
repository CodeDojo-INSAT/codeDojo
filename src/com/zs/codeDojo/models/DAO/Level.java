package com.zs.codeDojo.models.DAO;

public class Level {
    private int levelId;
    private String title;

    public Level() {
    }

    public Level(int levelId,String title) {
        this.levelId = levelId;
        this.title = title;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

   
}
