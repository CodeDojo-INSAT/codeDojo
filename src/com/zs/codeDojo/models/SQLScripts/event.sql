CREATE EVENT schedule_publish_event
ON SCHEDULE EVERY 10 SECOND
DO
BEGIN
    SET @current_time = CURTIME();
    SET @current_date = CURDATE();

    SELECT schedule_id INTO @scheduled_question_id
    FROM schedule_questions
    WHERE publish_date = @current_date and publish_time BETWEEN @current_time AND ADDTIME(@current_time, '00:01:00');

    IF @scheduled_question_id IS NOT NULL THEN
        CALL publish_question(@scheduled_question_id);
    END IF;
END;