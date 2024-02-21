package com.zs.codeDojo.models.DAO;

public class Level {
    private int levelId;
    private int levelNumber;
    private String title;

    public Level() {
    }

    public Level(int levelId, int levelNumber, String title) {
        this.levelId = levelId;
        this.levelNumber = levelNumber;
        this.title = title;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Level{" +
                "levelId=" + levelId +
                ", levelNumber=" + levelNumber +
                ", title='" + title + '\'' +
                '}';
    }
}
