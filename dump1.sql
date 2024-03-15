        drop table if exists Options;
        drop table if exists QuizQuestions;
        drop table if exists QuizSubmissions;
        drop table if exists Quizzes;

        create table Quizzes (
            QuizID VARCHAR(64),
            QuizName VARCHAR(16),
            QuizType enum("TEX", "MCQ"),
            QuestionCount smallint,
            CreateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            UpdateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            PRIMARY KEY (QuizID)
        );

        create table QuizQuestions (
            QuizID VARCHAR(64),
            QuestionID VARCHAR(8),
            QuestionText VARCHAR(256),
            Answer VARCHAR(64),
            PRIMARY KEY (QuestionID, QuizID),
            FOREIGN KEY (QuizID) REFERENCES Quizzes(QuizID) ON DELETE CASCADE
        );

        create table Options (
            QuizID VARCHAR(64),
            QuestionID VARCHAR(8),
            OptionID VARCHAR(8),
            OptionText VARCHAR(256),
            IsAnswer ENUM("true", "false") DEFAULT "false",
            PRIMARY KEY (OptionID, QuestionID, QuizID),
            FOREIGN KEY (QuizID) REFERENCES Quizzes(QuizID) ON DELETE CASCADE,
            FOREIGN KEY (QuestionID) REFERENCES QuizQuestions(QuestionID) ON DELETE CASCADE
        );

        create table QuizSubmissions (
            Username VARCHAR(64),
            QuizID VARCHAR(64),
            SubmissionSequence VARCHAR(100),
            CorrectCount INTEGER,
            SubmissionStatus ENUM("PASS", "FAIL") DEFAULT "FAIL",
            SubmissionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            PRIMARY KEY (Username, QuizID, SubmissionDate),
            FOREIGN KEY (Username) REFERENCES Users(Username) ON DELETE CASCADE,
            FOREIGN KEY (QuizID) REFERENCES Quizzes(QuizID) ON DELETE CASCADE
        );

