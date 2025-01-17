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

    public static final String GET_STREAK = "SELECT count(*) AS cnt FROM (SELECT activity_date, DATE_ADD(activity_date, INTERVAL -(@row_number := @row_number + 1) DAY) AS base_date FROM user_activity WHERE username = ? ORDER BY activity_date) AS t GROUP BY base_date ORDER BY cnt LIMIT 1";
    public static final String UPDATE_STREAK = "INSERT INTO user_activity(username, activity_date) VALUES(?, ?)";
    public static final String IS_COMPLETED = "SELECT * FROM user_activity WHERE DATE(activity_date) = CURDATE() AND username = ?";

    public static final String DELETE_PROCEDURE = "DROP PROCEDURE IF EXISTS publish_question;";
    public static final String DELETE_EVENT = "DROP EVENT IF EXISTS schedule_publish_event;";

    public static final String GET_TODAY_QUESTION_TEST_CASES = "SELECT input_value, expected_value FROM TestCases WHERE question_id = ("
            + "SELECT question_id FROM Questions, schedule_questions AS s WHERE s.publish_date = ? AND s.is_published = 'Y')";
    public static final String GET_TEST_CASES_COUNT = "SELECT count(*) FROM (" + GET_TODAY_QUESTION_TEST_CASES
            + ") AS t";

    public static final String INSERT_QUERY = "INSERT INTO questions VALUES(?, ?, ?)";
    public static final String READ_QUERY = "SELECT description, questionCode FROM questions WHERE level = ?";

    public static final String GET_TYPE_OF_QUESTION = "SELECT checkerId FROM QCheckerRelation WHERE levelID = ?";
    public static final String GET_CHECKER_CLASS_NAME = "SELECT class_name FROM checkers WHERE id = ?";

    public static final String GET_TEST_CASES_CHECKER = "SELECT input_value, expected_value FROM CourseTestcases WHERE question_id = ?";
    public static final String GET_TEST_CASES_CHECKER_COUNT = "SELECT count(*) FROM (" + GET_TEST_CASES_CHECKER
            + ") AS t";

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

    // quizes query goes hereeee

    public static final String CREATE_QUIZ = "insert into Quizzes Values (?, ?, ?, ?, ?, ?);";
    public static final String GET_QUIZ = "SELECT * FROM Quizzes WHERE QuizID = ?;";
    public static final String GET_QUIZZES = "SELECT * FROM Quizzes;";
    public static final String DELETE_QUIZ = "DELETE FROM Quizzes WHERE QuizID = ?;";
    public static final String GET_QUESTIONS_MINMAL = "SELECT QuestionID, QuestionText FROM QuizQuestions WHERE QuizID = ?;";
    public static final String GET_QUIZZES_MINIMAL = "SELECT QuizID, QuizName, QuizType FROM Quizzes;";

    public static final String GET_CORRECT_ANSWER_TEXT = "SELECT Answer from QuizQuestions WHERE QuizID = ? AND QuestionID = ?;";
    public static final String GET_CORRECT_OPTION = "SELECT OptionID from Options WHERE QuizID = ? AND QuestionID = ? AND IsAnswer = 'true';";
    public static final String GET_OPTIONS_MINIMAL = "SELECT OptionID, OptionText FROM Options WHERE QuizID = ? AND QuestionID = ?;";
    public static final String CREATE_QUESTION = "insert into QuizQuestions Values (?, ?, ?, ?);";

    public static final String CREATE_OPTION = "insert into Options Values (?, ?, ?, ?, ?);";

    // Course query goes hereeeeeeeeeeeeeeeeeeee:+{S[;s's:s[]]}

    public static final String GET_SUBMITTED_CODE = "SELECT code FROM UserSubmission where username = ? and levelID = ?;";
    public static final String GET_CURRENT_LEVEL = "Select level from UserCurrentLevel where username = ?;";
    public static final String UPDATE_CURRENT_LEVEL = "UPDATE UserCurrentLevel set level = ? WHERE username = ? ;";
    public static final String GET_LEVELS_METADATA = "SELECT CourseQuestions.levelID, CourseQuestions.title, CourseQuestions.description, CASE WHEN UserSubmission.levelId IS NOT NULL THEN 'true' ELSE 'false' END AS completed FROM CourseQuestions LEFT JOIN UserSubmission ON CourseQuestions.levelID = UserSubmission.levelId";

    public static final String ADD_QUIZ_SUBMISSION = "insert into QuizSubmissions Values (?, ?, ?, ?, ?, ?);";
    public static final String GET_LATEST_QUIZ_SUBMISSION = "SELECT * FROM QuizSubmissions WHERE Username = ? AND QuizID = ? ORDER BY SubmissionDate DESC LIMIT 1;";
    public static final String GET_LATEST_PASSED_SUBMISSION = "SELECT * FROM QuizSubmissions WHERE Username = ? AND QuizID = ? AND SubmissionStatus = \"PASS\" ORDER BY SubmissionDate ASC LIMIT 1;";

    public static final String GET_LEVEL_DETAILS = "SELECT * from Levels where levelID = ?;";
    public static final String GET_QUESTION = "SELECT * from CourseQuestions WHERE levelID = ?;";

    public static final String ADD_SUBMISSION = "INSERT INTO UserSubmission (levelId,username,code) VALUES(?,?,?);";
    public static final String UPDATE_SUBMISSION = "UPDATE UserSubmission  SET code = ?  WHERE username = ? AND levelID = ?;";
    public static final String GET_ALL_PASSED_SUBMISSION = "SELECT DISTINCT QuizID FROM QuizSubmissions WHERE Username = ? AND SubmissionStatus = \"PASS\";";

    // Announcements Queries go Here...
    public static final String CREATE_ANNOUNCEMENT = "insert into announcements (announcementTitle, announcementContent, createdDate, updatedDate) Values (?, ?, ?, ?)";

    // Tournament api starts here.........
    public static final String ADD_STREAKS = "UPDATE UserShurikan SET shurikan = ? WHERE username = ?;";
    public static final String GET_CURRENT_STREAKS = "SELECT shurikan from UserShurikan WHERE username = ?;";
    public static final String GET_LEADERBOAD_PLAYERS ="SELECT u.username,u.id,u.shurikan,un.firstname FROM UserShurikan as u LEFT JOIN Users as un on u.username = un.username ORDER BY shurikan DESC LIMIT 10";
}