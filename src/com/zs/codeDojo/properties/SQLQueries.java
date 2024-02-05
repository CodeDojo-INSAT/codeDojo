package com.zs.codeDojo.properties;

public class SQLQueries {
    public static final String ADD_QUESTION_QUERY = "INSERT INTO Questions VALUES(?, ?, ?, ?, ?)";
    public static final String ADD_TEST_CASES_QUERY = "INSERT INTO TestCases VALUES(?, ?, ?, ?)";
    public static final String GET_LAST_ID_QUERY = "SELECT count(*) FROM ";
    public static final String PUBLISH_QUESTION = "INSERT INTO schedule_questions VALUES(?, ?, ?, ?, ?)";
    public static final String GET_TODAY_QUESTION = "SELECT title, description FROM Questions, schedule_questions as s WHERE s.publish_date = ? and s.is_published = 'Y'";
    public static final String GET_TODAY_QUESTION_PUBLISHING_TIME = "SELECT publish_time FROM schedule_questions WHERE publish_date = ?";

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
}
