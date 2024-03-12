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
// import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zs.codeDojo.models.auth.AuthStatus;
import com.zs.codeDojo.properties.SQLQueries;
// import com.google.gson.JsonArray;
import com.zs.codeDojo.controllers.quizz.*;

public class DBModule {
    private Connection conn = null;

    private String error = null;
    private String publishingTime = null;
    private Question todayQuestion = null;

    public DBModule(Connection conn) {
        this.conn = conn;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // daily Question_start
    public boolean addQuestion(JSONObject json, boolean withTestCases) {
        JSONObject question = json.getJSONObject("question");
        boolean status = false;

        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.ADD_QUESTION_QUERY)) {
            int questionId = getLastId("Questions");
            statement.setInt(1, questionId);
            statement.setString(2, question.getString("title"));
            statement.setString(3, question.getString("description"));
            statement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            statement.setTime(5, new java.sql.Time(System.currentTimeMillis()));

            if (statement.executeUpdate() != 0) {
                if (withTestCases) {
                    JSONArray tcArray = json.getJSONArray("testCases");
                    for (int i = 0; i < tcArray.length(); i++) {
                        JSONObject tcJsonObject = tcArray.getJSONObject(i);
                        addTestCases(questionId, tcJsonObject.getString("input"), tcJsonObject.getString("output"));
                    }
                } else {
                    status = true;
                }

                status = publishQuestion(questionId, json);
            }

            conn.commit();
        } catch (SQLException ex) {
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
        } else {
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
        } catch (ParseException e) {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lastId + 1;
    }

    // get question start
    public boolean fetchTodayQuestion() {
        boolean status = false;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.GET_TODAY_QUESTION)) {
            java.sql.Date current_date = new java.sql.Date(System.currentTimeMillis());
            statement.setString(1, current_date.toString());

            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                if (!rs.isBeforeFirst()) {
                    try (PreparedStatement statement1 = conn
                            .prepareStatement(SQLQueries.GET_TODAY_QUESTION_PUBLISHING_TIME)) {
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
                } else {
                    while (rs.next()) {
                        String title = rs.getString("title");
                        String description = rs.getString("description");

                        todayQuestion = new Question(title, description);
                    }
                    rs.close();

                    status = true;
                }
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return status;
    }
    // get Question end

    // get streak start.
    public int getStreakForUser(String username) {
        int streak = -1;
        try (PreparedStatement statement = conn.prepareStatement("set @row_number = 0")) {
            statement.execute();

            try (PreparedStatement statement1 = conn.prepareStatement(SQLQueries.GET_STREAK)) {
                statement1.setString(1, username);

                if (statement1.execute()) {
                    ResultSet rs = statement1.getResultSet();

                    while (rs.next()) {
                        streak = rs.getInt("cnt");
                    }
                    rs.close();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return streak;
    }

    public int updateStreak(String username) {
        int status = -1;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.UPDATE_STREAK)) {
            statement.setString(1, username);
            statement.setDate(2, new java.sql.Date(System.currentTimeMillis()));

            status = statement.executeUpdate();
            conn.commit();
        }
        catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }

        return status;
    }

    public boolean isCompleted(String username) {
        boolean flag = false;

        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.IS_COMPLETED)) {
            statement.setString(1, username);

            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                flag = rs.next();
                rs.close();
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return flag;
    }
    // get streak end.

    public TestCases getTodayQuestionTestCases() {
        TestCases testCases = null;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.GET_TODAY_QUESTION_TEST_CASES)) {
            java.sql.Date current_date = new java.sql.Date(System.currentTimeMillis());
            statement.setDate(1, current_date);

            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                if (!rs.isBeforeFirst()) {
                    error = "No today question available.";
                } else {
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

                    int i = 0;
                    while (rs.next()) {
                        input[i] = rs.getString("input_value");
                        output[i++] = rs.getString("expected_value");
                    }
                    testCases = new TestCases(input, output);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return testCases;
    }

    public boolean deleteQuestion(int id) {
        boolean status = false;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.DELETE_QUESTION)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() != 0) {
                status = true;
            }
            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
        return status;
    }

    public String[] fetchAllTitles() {
        String[] res = null;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.FETCH_TITLES)) {
            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                String content = "";
                while (rs.next()) {
                    content += rs.getString(1) + "#EOL#";
                }
                rs.close();

                res = content.split("#EOL#");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public boolean updateQuestion(int id, String title, String question) {
        boolean status = false;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.UPDATE_QUESTION)) {
            statement.setString(1, title);
            statement.setString(2, question);
            statement.setTime(3, new java.sql.Time(System.currentTimeMillis()));
            statement.setInt(4, id);

            if (statement.executeUpdate() != 0) {
                status = true;
            }
            conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
        return status;
    }

    public TestCases fetchTestCase(int id) {
        TestCases testCases = null;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.FETCH_TESTCASE)) {
            statement.setInt(1, id);

            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                String input = "";
                String output = "";
                String tcId = "";

                while (rs.next()) {
                    tcId += rs.getString("id") + "#eof#";
                    input += rs.getString("input_value") + "#eof#";
                    output += rs.getString("expected_value") + "#eof#";
                }

                testCases = new TestCases(input.split("#eof#"), output.split("#eof#"), tcId.split("#eof#"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return testCases;
    }

    public boolean updateTestcases(int tcId, String input, String output, boolean commit) {
        boolean status = false;

        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.UPDATE_TESTCASE)) {
            statement.setString(1, input);
            statement.setString(2, output);
            statement.setInt(3, tcId);

            if (statement.executeUpdate() != 0) {
                status = true;
            }

            if (commit)
                conn.commit();
        } catch (SQLException ex) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
        }
        return status;
    }
    // daily question end

    // main
    public Question readContentFromDatabase(int level) {
        String description = null;
        String code = null;
        try (PreparedStatement preparedStatement = conn.prepareStatement(SQLQueries.READ_QUERY)) {
            preparedStatement.setInt(1, level);

            if (preparedStatement.execute()) {

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
        } catch (SQLException ex) {
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
        } catch (SQLException ex) {
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
                int i = 0;
                while (rs.next()) {
                    input[i] = rs.getString("input_value");
                    output[i++] = rs.getString("expected_value");
                }
                rs.close();

                testCases = new TestCases(input, output);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testCases;
    }

    // main section end

    // auth section start
    public AuthStatus authenticate(User user) {
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
        } catch (SQLException e) {
            return new AuthStatus("406", e);
        }
    }

    public boolean isExistingUser(User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_USERNAME_PS);
        stmt.setString(1, user.getUsername());

        ResultSet rs = stmt.executeQuery();

        boolean status = rs.next();
        rs.close();
        stmt.close();

        return status;
    }

    public boolean isExistingUser(String user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(SQLQueries.SELECT_USERNAME_PS);
        stmt.setString(1, user);

        ResultSet rs = stmt.executeQuery();

        boolean status = rs.next();
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

    public AuthStatus createUser(User user) {
        try {
            if (user.isEmpty()) {
                return new AuthStatus("405");
            }

            if (isExistingEmail(user)) {
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
        } catch (SQLException e) {
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
                    Boolean.parseBoolean(resultSet.getString(5)));
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

            if (!resultSet.next()) {
                return new AuthStatus("409");
            }

            statement = conn.prepareStatement(SQLQueries.UPDATE_VERIFIED_PS);

            statement.setString(1, "true");
            statement.setString(2, user.getEmail());

            statement.execute();
            conn.commit();
            return new AuthStatus("205");
        } catch (SQLException ex) {
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
            Timestamp instant = Timestamp.from(Instant.now());

            PreparedStatement statement = conn.prepareStatement(SQLQueries.CREATE_VERIFY_CODE);

            statement.setString(1, user.getEmail());
            statement.setString(2, code);
            statement.setString(3, instant.toString());

            statement.execute();
            conn.commit();
        } catch (SQLException e) {
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

        if (resultSet.next()) {
            return resultSet.getString(1);
        }

        return null;
    }

    public void deleteVerifyCode(User user) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(SQLQueries.DELETE_VERIFY_CODE);
        statement.setString(1, user.getEmail());
        statement.execute();
    }

    // auth section end

    // admin section start
    public int fetchLevels() {
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.FETCH_LEVEL)) {
            ResultSet rs = statement.executeQuery();

            rs.next();

            int ret = rs.getInt(1);
            rs.close();

            return ret;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return -1;
        }
    }

    public Question readQuestionFromDatabase(int level) {
        String questionCode = null;
        String description = null;

        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.READ_Q_D)) {
            statement.setInt(1, level);

            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();
                rs.next();

                description = rs.getString(1);
                questionCode = rs.getString(2);
                rs.close();
                return new Question(description, questionCode);
            } else {
                return null;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public boolean updateLevel(String desc, String question, int level) {
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.UPDATE_LEVEL)) {
            statement.setString(1, desc);
            statement.setString(2, question);
            statement.setInt(3, level);

            boolean status = statement.executeUpdate() == 1;

            conn.commit();
            return status;
        } catch (SQLException sqlEx) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sqlEx.printStackTrace();
            return false;
        }
    }

    public boolean uploadLevel(String desc, String question, int[] checkers) {
        boolean status = false;
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.UPLOAD_LEVEL)) {
            int currentLevel = fetchLevels();
            statement.setInt(1, currentLevel + 1);
            statement.setString(2, desc);
            statement.setString(3, question);

            if (statement.executeUpdate() == 1) {
                try (PreparedStatement statement2 = conn.prepareStatement(SQLQueries.CHECKER_RELATION)) {
                    for (int i = 0; i < checkers.length; i++) {
                        statement2.setInt(1, currentLevel + 1);
                        statement2.setInt(2, checkers[i]);

                        if (statement2.executeUpdate() != 1) {
                            return false;
                        }
                    }
                }
                status = true;
            }

            conn.commit();
            return status;
        } catch (SQLException sqlEx) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sqlEx.printStackTrace();
            return false;
        }
    }

    public String[] fetchCheckers() {
        String res = "";
        try (PreparedStatement statement = conn.prepareStatement(SQLQueries.FETCH_CHECKER)) {
            if (statement.execute()) {
                ResultSet rs = statement.getResultSet();

                while (rs.next()) {
                    String name = rs.getString(1);
                    res += name + "#EOL#";
                }
                rs.close();
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return res.split("#EOL#");
    }
    // admin section end

    // Quiz section start

    public JSONObject getQuestions(String quizID) {
        JSONObject res = new JSONObject();
        JSONObject info = getQuizInfo(quizID);

        if (info != null && info.isEmpty()) {
            return res;
        }

        res.put("quizInfo", info);

        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.GET_QUESTIONS_MINMAL)) {
            stmt.setString(1, quizID);
            JSONArray questions = new JSONArray();
            if (stmt.execute()) {
                ResultSet resSet = stmt.getResultSet();

                while (resSet.next()) {
                    JSONObject obj = new JSONObject();
                    String questionID = resSet.getString(1);
                    String questionText = resSet.getString(2);

                    obj.put("questionID", questionID);
                    obj.put("questionText", questionText);

                    if (info.getString("quizType").equals("MCQ")) {
                        obj.put("options", getOptions(quizID, questionID));
                    }

                    questions.put(obj);

                }

                res.put("questions", questions);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public JSONArray getOptions(String quizID, String questionsID) {
        JSONArray res = new JSONArray();
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.GET_OPTIONS_MINIMAL)) {
            stmt.setString(1, quizID);
            stmt.setString(2, questionsID);
            ResultSet resSet = stmt.executeQuery();

            while (resSet.next()) {
                JSONObject option = new JSONObject();
                option.put("OptionID", resSet.getString(1));
                option.put("OptionText", resSet.getString(2));

                res.put(option);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public Status deleteQuiz(String quizID) {
        Status status = null;
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.DELETE_QUIZ)) {
            stmt.setString(1, quizID);
            if (stmt.executeUpdate() == 1) {
                status = new Status("802");
            } else {
                status = new Status("410");
            }
            conn.commit();
        } catch (SQLException e) {
            try {

                conn.rollback();

            } catch (SQLException e1) {

                e1.printStackTrace();
            }
            return new Status("406");
        }
        return status;
    }

    public JSONObject getQuizInfo(String quizID) {
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.GET_QUIZ)) {

            stmt.setString(1, quizID);

            if (stmt.execute()) {
                ResultSet res = stmt.getResultSet();
                if (res.next()) {
                    String quizName = res.getString("QuizName");
                    String quizType = res.getString("QuizType");
                    int numQuestions = res.getInt("QuestionCount");
                    String createdOn = res.getString("CreateDate");
                    String lastUpdated = res.getString("UpdateDate");
                    return new QuizObject(quizID, quizName, quizType, createdOn, lastUpdated, numQuestions).toJSON();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    public JSONArray getQuizzes() {
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.GET_QUIZZES)) {

            JSONArray arr = new JSONArray();
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                String quizID = res.getString(1);
                String quizName = res.getString(2);
                String quizType = res.getString(3);
                int numQuestions = res.getInt(4);
                String createdOn = res.getString(5);
                String lastUpdated = res.getString(6);
                arr.put(new QuizObject(quizID, quizName, quizType, createdOn, lastUpdated, numQuestions).toJSON());
            }
            stmt.close();
            return arr;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }

    // private String getAnswerOption(String quizID, String questionID){
    // try (PreparedStatement stmt =
    // conn.prepareStatement(SQLQueries.GET_CORRECT_ANSWER_TEXT)){
    // stmt.setString(1, quizID);
    // stmt.setString(2, questionID);
    // if (stmt.execute()){
    // ResultSet resultSet = stmt.getResultSet();
    // return resultSet.getString(1);
    // }
    // }catch (SQLException e){
    // e.printStackTrace();
    // }

    // return null;
    // }

    private String getAnswerText(String quizID, String questionID) {
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.GET_CORRECT_ANSWER_TEXT)) {
            stmt.setString(1, quizID);
            stmt.setString(2, questionID);

            if (stmt.execute()) {
                ResultSet resultSet = stmt.getResultSet();
                resultSet.next();
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isCorrectAnswer(String quizID, String questionID, String response) {
        String answer;

        answer = getAnswerText(quizID, questionID);
        System.out.println(answer);

        return answer != null && answer.equals(response);
    }

    public Status createQuiz(QuizExternObject obj) {
        String quizID = obj.getQuizID();
        String quizName = obj.getQuizName();
        String quizType = obj.getQuizType();
        JSONArray questions = obj.getQuestions();
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.CREATE_QUIZ)) {

            stmt.setString(1, quizID);
            stmt.setString(2, quizName);
            stmt.setString(3, quizType);
            stmt.setInt(4, questions.length());
            Timestamp createTime = new Timestamp(new java.util.Date().getTime());
            stmt.setTimestamp(5, createTime);
            stmt.setTimestamp(6, createTime);

            stmt.execute();

            conn.commit();

        } catch (SQLException e) {
            try {

                conn.rollback();

            } catch (Exception ei) {

                ei.printStackTrace();
            }

            return new Status("406", e);
        }

        try {
            for (int i = 0; i < questions.length(); i++) {
                JSONObject questionObj = questions.getJSONObject(i);
                PreparedStatement stmt = conn.prepareStatement(SQLQueries.CREATE_QUESTION);
                stmt.setString(1, quizID);
                stmt.setString(2, "q" + (i + 1));
                stmt.setString(3, questionObj.getString("question"));
                stmt.setString(4, "o" + questionObj.getInt("answer"));

                stmt.execute();
                stmt.close();
                if (quizType.equals("MCQ")) {
                    JSONArray optionsObj = questionObj.getJSONArray("options");
                    for (int j = 0; j < optionsObj.length(); j++) {
                        PreparedStatement optionStmt = conn.prepareStatement(SQLQueries.CREATE_OPTION);
                        optionStmt.setString(1, quizID);
                        optionStmt.setString(2, "q" + (i + 1));
                        optionStmt.setString(3, "o" + (j + 1));

                        optionStmt.setString(4, optionsObj.getString(j));
                        if (("o" + (j + 1)).equals("o" + questionObj.getInt("answer"))) {
                            optionStmt.setString(5, "true");
                        } else {
                            optionStmt.setString(5, "false");
                        }

                        optionStmt.execute();
                        optionStmt.close();
                    }
                }

            }
            conn.commit();
        } catch (SQLException e) {
            try {

                conn.rollback();

            } catch (SQLException e1) {

                e1.printStackTrace();
            }
            return new Status("406", e);
        }
        return new Status("800");
    }

    // Course starts here
    // ..............................................................................................

    public int getCurrentLevel(User user) {

        try {

            String username = user.getUsername();

            PreparedStatement stm = conn.prepareStatement(SQLQueries.GET_CURRENT_LEVEL);
            stm.setString(1, username);

            System.out.println(stm.toString());
            ResultSet res = stm.executeQuery();
            res.next();

            return res.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;

    }

    public Status updateCurrentLevel(User user) {

        try {
            int level = getCurrentLevel(user);

            PreparedStatement stm = conn.prepareStatement(SQLQueries.UPDATE_CURRENT_LEVEL);
            stm.setString(1, String.valueOf(level + 1));
            stm.setString(2, user.getUsername());

            stm.executeUpdate();

            return new Status("100");

        } catch (Exception e) {

            return new Status("406", e);
        }

    }

    public CourseQuestion getCourseQuestion(String requestedLevel) {

        // CourseQuestion question;

        try {

            PreparedStatement
             stm = conn.prepareStatement(SQLQueries.GET_QUESTION);
            stm.setString(1, String.valueOf(requestedLevel));

            ResultSet result = stm.executeQuery();
            result.next();

            return new CourseQuestion(result.getString("levelID"), result.getString("title"),
                    result.getString("description"), result.getString("code"));

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    public JSONArray getCourseMetaData(){
        
        JSONArray json = new JSONArray();
        try {
            
            PreparedStatement stm = conn.prepareStatement(SQLQueries.GET_LEVELS_METADATA);
            ResultSet result = stm.executeQuery();

            while (result.next()) {
                JSONObject jobj = new JSONObject();

                jobj.put("Title", result.getString("title"));
                jobj.put("LevelID",result.getString("levelID"));

                json.put(jobj);
    
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    public Status addSubmission(String user,int level,String code ){

        
        try {
            PreparedStatement stm = conn.prepareStatement(SQLQueries.ADD_SUBMISSION);

            stm.setInt(1,level);
            stm.setString(2,user);
            stm.setString(3,code);
            stm.executeUpdate();
            return new Status("102");

        } catch (Exception e) {
            e.printStackTrace();
           return new Status("406");
        }

    } 

    public Status updateSubmission(String user,int level,String code ){

        
        try {
            PreparedStatement stm = conn.prepareStatement(SQLQueries.ADD_SUBMISSION);

            stm.setString(1,code);
            stm.setString(2,user);
            stm.setInt(3,level);
            stm.executeUpdate();
            return new Status("102");

        } catch (Exception e) {
            e.printStackTrace();
           return new Status("406");
        }

    } 

    // Announcement modules..............

    public Status createAnnouncement(AnnouncementObject obj) {
        try (PreparedStatement stmt = conn.prepareStatement(SQLQueries.CREATE_ANNOUNCEMENT)) {
            stmt.setString(1, obj.getAnnouncementTitle());
            stmt.setString(2, obj.getAnnouncementContent());
            Timestamp createTime = new Timestamp(new java.util.Date().getTime());
            stmt.setTimestamp(3, createTime);
            stmt.setTimestamp(4, createTime);
            stmt.execute();

            return new Status("600");

        } catch (SQLException e) {
            return new Status("406");
        }
    }

    // Tournament starts here......

    public int getCurrentStreak(User user){
        int streak =0;
        try (PreparedStatement stm = conn.prepareStatement(SQLQueries.GET_CURRENT_STREAKS)){

            stm.setString(1,user.getUsername());
            ResultSet result = stm.executeQuery();
            result.next();
            streak = result.getInt("streak");
            return streak;
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Status addStreaks(User user , int streak){
        try(PreparedStatement stm = conn.prepareStatement(SQLQueries.ADD_STREAKS)) {

            int currentStreak = getCurrentStreak(user);
            stm.setInt(1,currentStreak + streak);
            stm.setString(2, user.getUsername());
            stm.executeUpdate();
            return new Status("111");

        } catch (Exception e) {
            return new Status("406");
        }
    }
    
    //Tournament ends here

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

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