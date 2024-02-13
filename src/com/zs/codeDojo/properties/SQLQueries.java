package com.zs.codeDojo.properties;

public class SQLQueries {
    public static final String ADD_QUESTION_QUERY = "INSERT INTO Questions VALUES(?, ?, ?, ?, ?)";
    public static final String ADD_TEST_CASES_QUERY = "INSERT INTO TestCases VALUES(?, ?, ?, ?)";
    public static final String GET_LAST_ID_QUERY = "SELECT count(*) FROM ";
    public static final String PUBLISH_QUESTION = "INSERT INTO schedule_questions VALUES(?, ?, ?, ?, ?)";
    public static final String GET_TODAY_QUESTION = "SELECT title, description FROM Questions, schedule_questions as s WHERE s.publish_date = ? AND s.is_published = 'Y'";
    public static final String GET_TODAY_QUESTION_PUBLISHING_TIME = "SELECT publish_time FROM schedule_questions WHERE publish_date = ?";
    public static final String FETCH_TITLES = "SELECT title FROM Questions";
    public static final String UPDATE_QUESTION = "UPDATE Questions SET title = ?, description = ?, modified_at = ? WHERE id = ?";
    public static final String FETCH_TESTCASE = "SELECT id, input_value, expected_value FROM TestCases WHERE question_id = ?";
    public static final String UPDATE_TESTCASE = "UPDATE TestCases SET input_value = ?, expected_value = ? WHERE id = ?";

    public static final String GET_STREAK = "SELECT count(*) AS cnt FROM "
                                    + "(SELECT activity_date, " 
                                    + "DATE_ADD(activity_date, INTERVAL -(@row_number := @row_number + 1) DAY) AS base_date "
                                    + "FROM user_activity WHERE user_id = ? ORDER BY activity_date) "
                                    + "AS t GROUP BY base_date ORDER BY cnt LIMIT 1";

    public static final String DELETE_PROCEDURE = "DROP PROCEDURE IF EXISTS publish_question;";
    public static final String DELETE_EVENT = "DROP EVENT IF EXISTS schedule_publish_event;";

    public static final String GET_TODAY_QUESTION_TEST_CASES = "SELECT input_value, expected_value FROM TestCases WHERE question_id = ("
                                                             + "SELECT question_id FROM Questions, schedule_questions AS s WHERE s.publish_date = ? AND s.is_published = 'Y')";
    public static final String GET_TEST_CASES_COUNT = "SELECT count(*) FROM (" + GET_TODAY_QUESTION_TEST_CASES + ") AS t";

    public static final String INSERT_QUERY = "INSERT INTO questions VALUES(?, ?, ?)";
    public static final String READ_QUERY = "SELECT description, questionCode FROM questions WHERE level = ?";

    public static final String GET_TYPE_OF_QUESTION = "SELECT checkerId FROM QCheckerRelation WHERE level = ?";
    public static final String GET_CHECKER_CLASS_NAME = "SELECT class_name FROM checkers WHERE id = ?";

    public static final String GET_TEST_CASES_CHECKER = "SELECT input_value, expected_value FROM testcases_checker WHERE question_id = ?";
    public static final String GET_TEST_CASES_CHECKER_COUNT = "SELECT count(*) FROM (" + GET_TEST_CASES_CHECKER + ") AS t";

    public static final String SELECT_USERNAME_PS = "SELECT * FROM Users WHERE username = ?;";
    public static final String DELETE_QUESTION = "DELETE FROM Questions WHERE id = ?";

    public static final String SELECT_VERIFY_CODE = "SELECT code FROM VerifyCodes WHERE email = ?";
    public static final String CREATE_VERIFY_CODE = "INSERT INTO VerifyCodes VALUES (?, ?, ?);";
    public static final String SELECT_EMAIL_PS = "SELECT * FROM Users WHERE email = ?;";
    public static final String SELECT_USERNAME_PASS_PS = "SELECT * FROM Users WHERE username = ? AND password = ?";
    public static final String INSERT_USER_PS = "INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_VERIFIED_PS = "UPDATE Users SET isVerified = ? WHERE email = ?";

    public static final String DELETE_VERIFY_CODE = "DELETE FROM VerifyCodes WHERE email = ?";
    public static final String SELECT_USER_DETAILS = "SELECT username, email, firstname, lastname, isVerified from Users where username = ?";

    public static final String FETCH_LEVEL = "SELECT COUNT(*) FROM questions";
    public static final String READ_Q_D = "SELECT description, questionCode FROM questions WHERE level = ?";
    public static final String UPDATE_LEVEL = "UPDATE questions SET description = ?, questionCode = ? WHERE level = ?";
    public static final String UPLOAD_LEVEL = "INSERT INTO questions VALUES(?, ?, ?)";
    public static final String FETCH_CHECKER = "SELECT name FROM checkers";
    public static final String CHECKER_RELATION = "INSERT INTO QCheckerRelation values(?, ?)";
}