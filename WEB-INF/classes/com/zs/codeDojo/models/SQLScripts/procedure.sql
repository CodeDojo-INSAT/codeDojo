CREATE PROCEDURE publish_question(IN questionId INT)
BEGIN
    UPDATE schedule_questions SET is_published = 'Y' WHERE question_id = questionId;
END;