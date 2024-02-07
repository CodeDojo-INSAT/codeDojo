package com.zs.codeDojo.models.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.codeDojo.models.auth.AuthStatus;
import com.zs.codeDojo.properties.SQLQueries;

public class DBModule {
    private Connection conn = null;

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

        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.ADD_QUESTION_QUERY)) {
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
        PreparedStatement statement1 = conn.prepareStatement(SQLQueries.ADD_TEST_CASES_QUERY);
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
        java.sql.Date publishDate = null;
        java.sql.Time publishTime = null;

        if (!publishNow) {
            publishDate = sqlDate(json.getString("publish_date"));
            publishTime = sqlTime(json.getString("publish_time"));
        }
        else {
            publishDate = new java.sql.Date(System.currentTimeMillis());
            publishTime = new java.sql.Time(System.currentTimeMillis());
        }
        
        PreparedStatement statement = conn.prepareStatement(SQLQueries.PUBLISH_QUESTION);
        statement.setInt(1, getLastId("schedule_questions"));
        statement.setInt(2, questionId);
        statement.setString(3, publishNow ? "Y" : "N");
        statement.setDate(4, publishDate);
        statement.setTime(5, publishTime);

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
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.GET_LAST_ID_QUERY + tableName)) {
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
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.GET_TODAY_QUESTION)){
            java.sql.Date current_date = new java.sql.Date(System.currentTimeMillis());
            statement.setString(1, current_date.toString());

            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                if (!rs.isBeforeFirst()) {
                    try (PreparedStatement statement1 = conn.prepareStatement(SQLQueries.GET_TODAY_QUESTION_PUBLISHING_TIME)) {
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
                else {
                    while (rs.next()) {
                        String title = rs.getString("title");
                        String description = rs.getString("description");

                        todayQuestion = new Question(title, description);
                    }
                    rs.close();

                    status = true;
                }
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

            try (PreparedStatement statement1 = conn.prepareStatement(SQLQueries.GET_STREAK)) {
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


    public TestCases getTodayQuestionTestCases() {
        TestCases testCases = null;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.GET_TODAY_QUESTION_TEST_CASES)){
            java.sql.Date current_date = new java.sql.Date(System.currentTimeMillis());
            statement.setDate(1, current_date);

            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                if (!rs.isBeforeFirst()) {
                    error = "No today question available.";
                }
                else {
                    int size = 0;
                    try (PreparedStatement statement1 = conn.prepareStatement(SQLQueries.GET_TEST_CASES_COUNT)) {
                        statement1.setDate(1, current_date);

                        if (statement1.execute()) {
                            ResultSet rs1 = statement1.getResultSet();

                            if (rs1.next()) {
                                size = rs1.getInt(1);
                            }
                            rs1.close();
                        }
                    }

                    String[] input = new String[size], output = new String[size];
                    
                    int i=0;
                    while (rs.next()) {
                        input[i] = rs.getString("input_value");
                        output[i++] = rs.getString("expected_value");
                    }
                    testCases = new TestCases(input, output);
                }
                rs.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return testCases;
    }


    //main
    public Question readContentFromDatabase(int level) {
        String description = null;
        String code = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(SQLQueries.READ_QUERY)) {
            preparedStatement.setInt(1, level);

            if (preparedStatement.execute()){
                
                ResultSet res = preparedStatement.getResultSet();
                while (res.next()) {
                    description = res.getString(1);
                    code = res.getString(2);
                }
    
                res.close();
            } else {
                System.out.println("Results not found");
            }

        } catch (SQLException psEx) {
            psEx.printStackTrace();
        }
        return new Question(description, code);
    }

    public void writeContentToDatabase(int level, String description, String code) {
        try (PreparedStatement preparedStatement = conn.prepareStatement(SQLQueries.INSERT_QUERY)) {
            preparedStatement.setInt(1, level);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, code);

            int rowsAffected = preparedStatement.executeUpdate();

            System.err.println("Rows affected: " + rowsAffected);
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }

    public int getTypeOfQuestion(int level) {
        int type = 0;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.GET_TYPE_OF_QUESTION)) {
            statement.setInt(1, level);

            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                while (rs.next()) {
                    type = rs.getInt(1);
                }

                rs.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return type;
    }


    public String getClassNameOfType(int type) {
        String className = null;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.GET_CHECKER_CLASS_NAME)) {
            statement.setInt(1, type);

            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                while (rs.next()) {
                    className = rs.getString("class_name");
                }

                rs.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        return className;
    }

    public TestCases getTestCases(int level) {
        TestCases testCases = null;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.GET_TEST_CASES_CHECKER)) {
            statement.setInt(1, level);

            if (statement.execute()) {
                int size = 0;
                try (PreparedStatement statement1 = conn.prepareStatement(SQLQueries.GET_TEST_CASES_CHECKER_COUNT)) {
                    statement1.setInt(1, level);

                    if (statement1.execute()) {
                        ResultSet rs1 = statement1.getResultSet();

                        while (rs1.next()) {
                            size = rs1.getInt(1);
                        }
                        rs1.close();
                    }
                }
                ResultSet rs = statement.getResultSet();

                String[] input = new String[size], output = new String[size];
                int i=0;
                while (rs.next()) {
                    input[i] = rs.getString("input_value");
                    output[i++] = rs.getString("expected_value");
                }
                rs.close();

                testCases = new TestCases(input, output);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return testCases;
    } 

    // main section end

    // auth section start
    public AuthStatus authenticate(User user){
        try {
            if (!isExistingUser(user)) {
                return new AuthStatus("404");
            }

            PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_USERNAME_PASS_PS);
            stmt.setString(1, user.getUsername());

            stmt.setString(2, user.getPassword());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new AuthStatus("200");
            }
            rs.close();
            stmt.close();

            return new AuthStatus("400");
        }catch (SQLException e){
            return new AuthStatus("406", e);
        }
    }

    public boolean isExistingUser(User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_USERNAME_PS);
        stmt.setString(1, user.getUsername());

        ResultSet rs = stmt.executeQuery();

        boolean status =  rs.next();
        rs.close();
        stmt.close();

        return status;
    }

    public boolean isExistingUser(String user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_USERNAME_PS);
        stmt.setString(1, user);

        ResultSet rs = stmt.executeQuery();

        boolean status =  rs.next();
        rs.close();
        stmt.close();

        return status;
    }

    public boolean isExistingEmail(User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_EMAIL_PS);
        stmt.setString(1, user.getEmail());

        ResultSet rs = stmt.executeQuery();

        boolean status = rs.next();
        rs.close();
        stmt.close();

        return status;
    }

    public boolean isExistingEmail(String email) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_EMAIL_PS);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();

        boolean status = rs.next();
        rs.close();
        stmt.close();

        return status;
    }

    public AuthStatus createUser(User user){
        try{
            if (user.isEmpty()){
                return new AuthStatus("405");
            }

            if (isExistingEmail(user)){
                return new AuthStatus("402");
            }

            if (isExistingUser(user)) {
                return new AuthStatus("401");
            }


            PreparedStatement stmt = conn.prepareStatement(SQLQueries.INSERT_USER_PS);

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, String.valueOf(user.isVerified()));


            if (stmt.executeUpdate() != 0) {
                conn.commit();
            }

            stmt.close();
            return new AuthStatus("201");
        }catch (SQLException e){
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return new AuthStatus("406", e);
        }
    }

    public User getUser(String username) throws SQLException {
        User user = null;
        PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_USER_DETAILS);
        stmt.setString(1, username);

        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.isBeforeFirst()) {
            resultSet.next();

            user = new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    null,
                    resultSet.getString(3),
                    resultSet.getString(4),
                    Boolean.parseBoolean(resultSet.getString(5))
            );
        }
        resultSet.close();
        stmt.close();

        return user;
    }

    public AuthStatus setVerified(User user) {
        try {
            PreparedStatement statement = conn.prepareStatement(SQLQueries.SELECT_EMAIL_PS);
            statement.setString(1, user.getEmail());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()){
                return new AuthStatus("409");
            }

            statement = conn.prepareStatement(SQLQueries.UPDATE_VERIFIED_PS);

            statement.setString(1, "true");
            statement.setString(2, user.getEmail());

            statement.execute();
            conn.commit();
            return new  AuthStatus("205");
        }
        catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        } 
        return null;
    }

    public void createVerifyCode(User user, String code) {
        try {
            Timestamp instant= Timestamp.from(Instant.now());

            PreparedStatement statement = conn.prepareStatement(SQLQueries.CREATE_VERIFY_CODE);

            statement.setString(1, user.getEmail());
            statement.setString(2, code);
            statement.setString(3, instant.toString());

            statement.execute();
            conn.commit();
        }
        catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public boolean verifyCodeExistsFor(User user) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SQLQueries.SELECT_VERIFY_CODE);
        statement.setString(1, user.getEmail());
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public String getVerifyCode(User user) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SQLQueries.SELECT_VERIFY_CODE);
        statement.setString(1, user.getEmail());
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            return resultSet.getString(1);
        }

        return null;
    }

    public void deleteVerifyCode(User user) throws SQLException{
        PreparedStatement statement = conn.prepareStatement(SQLQueries.DELETE_VERIFY_CODE);
        statement.setString(1, user.getEmail());
        statement.execute();
    }

    // auth section end

    public String getError() {
        return error;
    }

    public boolean hasError() {
        return error != null;
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
