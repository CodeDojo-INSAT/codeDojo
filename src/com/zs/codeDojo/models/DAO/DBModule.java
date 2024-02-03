package com.zs.codeDojo.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.codeDojo.models.dailyQuestions.Question;

public class DBModule {
    private Connection conn = null;
    private final String ADD_QUESTION_QUERY = "INSERT INTO Questions VALUES(?, ?, ?, ?, ?)";
    private final String ADD_TEST_CASES_QUERY = "INSERT INTO TestCases VALUES(?, ?, ?, ?)";
    private final String GET_LAST_ID_QUERY = "SELECT count(*) FROM ";
    private final String PUBLISH_QUESTION = "INSERT INTO schedule_questions VALUES(?, ?, ?, ?, ?)";
    private final String GET_TODAY_QUESTION = "SELECT title, description FROM Questions, schedule_questions as s WHERE s.publish_date = ? and s.is_published = 'Y'";
    private final String GET_TODAY_QUESTION_PUBLISHING_TIME = "SELECT publish_time FROM schedule_questions WHERE publish_date = ?";

    private final String GET_STREAK = "SELECT count(*) AS cnt FROM "
                                    + "(SELECT activity_date, " 
                                    + "DATE_ADD(activity_date, INTERVAL -(@row_number := @row_number + 1) DAY) AS base_date "
                                    + "FROM user_activity WHERE user_id = ? ORDER BY activity_date) "
                                    + "AS t GROUP BY base_date ORDER BY cnt LIMIT 1";

    private String error = null;
    private String publishingTime = null;
    private Question todayQuestion = null;

    public DBModule(Connection conn) {
        this.conn = conn;
    }

    // start post Question

    public boolean addQuestion(JSONObject json, boolean withTestCases) {
        JSONObject question = json.getJSONObject("question");
        boolean status = false;

        try (PreparedStatement statement = conn.prepareStatement(ADD_QUESTION_QUERY)) {
            conn.setAutoCommit(false);

            int questionId = getLastId("Questions");
            statement.setInt(1, questionId);
            statement.setString(2, question.getString("title"));
            statement.setString(3, question.getString("description"));
            statement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            statement.setTime(5, new java.sql.Time(System.currentTimeMillis()));
    
            if (statement.executeUpdate() != 0) {
                if (withTestCases) {
                    JSONArray tcArray = json.getJSONArray("testCases");
                    for (int i=0; i<tcArray.length(); i++) {
                        JSONObject tcJsonObject = tcArray.getJSONObject(i);
                        addTestCases(questionId, tcJsonObject.getString("input"), tcJsonObject.getString("output"));
                    }
                }
                else {
                    status = true;
                }

                status = publishQuestion(questionId, json);
            }

            conn.commit();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (ex instanceof SQLIntegrityConstraintViolationException) {
                error = "Already a question added for this day.";
            } 
        }
        return status;
    }

    private boolean addTestCases(int questionId, String input, String output) throws SQLException {        
        PreparedStatement statement1 = conn.prepareStatement(ADD_TEST_CASES_QUERY);
        statement1.setInt(1, getLastId("TestCases"));
        statement1.setInt(2, questionId);
        statement1.setString(3, input);
        statement1.setString(4, output);

        boolean status = statement1.executeUpdate() != 0;
        statement1.close();

        return status;
    }

    private boolean publishQuestion(int questionId, JSONObject json) throws SQLException {
        boolean status = false;
        boolean publishNow = json.getBoolean("publish_now");
        String publishDate = null, publishTime = null;

        if (!publishNow) {
            publishDate = json.getString("publish_date");
            publishTime = json.getString("publish_time");
        }
        
        PreparedStatement statement = conn.prepareStatement(PUBLISH_QUESTION);
        statement.setInt(1, getLastId("schedule_questions"));
        statement.setInt(2, questionId);
        statement.setString(3, publishNow ? "Y" : "N");
        statement.setDate(4, sqlDate(publishDate));
        statement.setTime(5, sqlTime(publishTime));

        if (statement.executeUpdate() != 0) {
            status = true;
        }
        statement.close();

        return status;
    }

    private java.sql.Date sqlDate(String date) {    
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date jDate = null;
        try {
            jDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Date(jDate.getTime());
    }

    private java.sql.Time sqlTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        Date date = null;
        try {
            date = format.parse(time);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return new java.sql.Time(date.getTime());
    }

    // end post question

    public int getLastId(String tableName) {
        int lastId = 0;
        try (PreparedStatement statement = conn.prepareStatement(GET_LAST_ID_QUERY + tableName)) {
            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();
                
                while (rs.next()) {
                    lastId = rs.getInt(1);
                }
                rs.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lastId + 1;
    }

    // get question start
    public boolean fetchTodayQuestion() {
        boolean status = false;
        try (PreparedStatement statement = conn.prepareStatement(GET_TODAY_QUESTION)){
            java.sql.Date current_date = new java.sql.Date(System.currentTimeMillis());
            statement.setString(1, current_date.toString());

            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                if (!rs.isBeforeFirst()) {
                    try (PreparedStatement statement1 = conn.prepareStatement(GET_TODAY_QUESTION_PUBLISHING_TIME)) {
                        statement1.setDate(1, current_date);

                        if (statement1.execute()) {
                            ResultSet rs1 = statement1.getResultSet();

                            if (rs1.isBeforeFirst()) {
                                while (rs1.next()) {
                                    publishingTime = rs1.getString("publish_time");
                                }
                                rs1.close();

                                return status;
                            }
                        }
                    }
                }

                while (rs.next()) {
                    String title = rs.getString("title");
                    String description = rs.getString("description");

                    todayQuestion = new Question(title, description);
                }
                rs.close();

                status = true;
            }
        }
        catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return status;
    }
    // get Question end

    //get streak start.
    public int getStreakForUser(int userId) {
        int streak = 0;
        try (PreparedStatement statement = conn.prepareStatement("set @row_number = 0")) {
            statement.execute();

            try (PreparedStatement statement1 = conn.prepareStatement(GET_STREAK)) {
                statement1.setInt(1, userId);
    
                if (statement1.execute()) {
                    ResultSet rs = statement1.getResultSet();
    
                    while (rs.next()) {
                        streak = rs.getInt("cnt");
                    }
                    rs.close();
                }
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return streak;
    }
    //get streak end.

    public String getError() {
        return error;
    }

    public boolean hasPublishingTime() {
        return publishingTime != null;
    }

    public String getPublishingTime() {
        return publishingTime;
    }

    public Question getTodayQuestion() {
        return todayQuestion;
    }
}
