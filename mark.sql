DROP TABLE IF EXISTS `Options`;
DROP TABLE IF EXISTS `QuizQuestions`;
DROP TABLE IF EXISTS `QuizSubmissions`;
DROP TABLE IF EXISTS `Quizzes`;





/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;


/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Quizzes` (
  `QuizID` varchar(64) NOT NULL,
  `QuizName` varchar(64) DEFAULT NULL,
  `QuizType` enum('TEX','MCQ') DEFAULT NULL,
  `QuestionCount` smallint(6) DEFAULT NULL,
  `CreateDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UpdateDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`QuizID`)
);
INSERT INTO `Quizzes` VALUES ('1372d8b9-ea1b-492d-b355-5b56d731301b','Inheritance','TEX',2,'2024-03-09 15:46:02','2024-03-09 15:46:02'),('f3839cce-504e-4fff-83ff-c84bc16e8e1f','Strings Recap','MCQ',2,'2024-03-09 15:59:46','2024-03-09 15:59:46');
/*!40000 ALTER TABLE `Quizzes` ENABLE KEYS */;

CREATE TABLE `QuizQuestions` (
  `QuizID` varchar(64) NOT NULL,
  `QuestionID` varchar(8) NOT NULL,
  `QuestionText` varchar(256) DEFAULT NULL,
  `Answer` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`QuestionID`,`QuizID`),
  KEY `QuizID` (`QuizID`),
  CONSTRAINT `QuizQuestions_ibfk_1` FOREIGN KEY (`QuizID`) REFERENCES `Quizzes` (`QuizID`) ON DELETE CASCADE
);

INSERT INTO `QuizQuestions` VALUES ('1372d8b9-ea1b-492d-b355-5b56d731301b','q1','what is the term used to describe the process on inheritting a class?','oinheritance'),('f3839cce-504e-4fff-83ff-c84bc16e8e1f','q1','Where are Strings Stored','o2'),('1372d8b9-ea1b-492d-b355-5b56d731301b','q2','how many classes can you inherit at a time','o1'),('f3839cce-504e-4fff-83ff-c84bc16e8e1f','q2','What Type of data is a String','o1');
/*!40000 ALTER TABLE `QuizQuestions` ENABLE KEYS */;

CREATE TABLE `Options` (
  `QuizID` varchar(64) NOT NULL,
  `QuestionID` varchar(8) NOT NULL,
  `OptionID` varchar(8) NOT NULL,
  `OptionText` varchar(256) DEFAULT NULL,
  `IsAnswer` enum('true','false') DEFAULT 'false',
  PRIMARY KEY (`OptionID`,`QuestionID`,`QuizID`),
  KEY `QuizID` (`QuizID`),
  KEY `QuestionID` (`QuestionID`),
  CONSTRAINT `Options_ibfk_1` FOREIGN KEY (`QuizID`) REFERENCES `Quizzes` (`QuizID`) ON DELETE CASCADE,
  CONSTRAINT `Options_ibfk_2` FOREIGN KEY (`QuestionID`) REFERENCES `QuizQuestions` (`QuestionID`) ON DELETE CASCADE
) ;

INSERT INTO `Options` VALUES ('f3839cce-504e-4fff-83ff-c84bc16e8e1f','q1','o1','In a Shoe Box','false'),('f3839cce-504e-4fff-83ff-c84bc16e8e1f','q2','o1','Non Primitive','true'),('f3839cce-504e-4fff-83ff-c84bc16e8e1f','q1','o2','In the Heap Memory','true'),('f3839cce-504e-4fff-83ff-c84bc16e8e1f','q2','o2','Primitive','false'),('f3839cce-504e-4fff-83ff-c84bc16e8e1f','q1','o3','In the Stack Memory','false'),('f3839cce-504e-4fff-83ff-c84bc16e8e1f','q2','o3','Extreme','false');

CREATE TABLE `QuizSubmissions` (
  `Username` varchar(64) NOT NULL,
  `QuizID` varchar(64) NOT NULL,
  `SubmissionSequence` varchar(100) DEFAULT NULL,
  `CorrectCount` int(11) DEFAULT NULL,
  `SubmissionStatus` enum('PASS','FAIL') DEFAULT 'FAIL',
  `SubmissionDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Username`,`QuizID`,`SubmissionDate`),
  KEY `QuizID` (`QuizID`),
  CONSTRAINT `QuizSubmissions_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `Users` (`username`),
  CONSTRAINT `QuizSubmissions_ibfk_2` FOREIGN KEY (`QuizID`) REFERENCES `Quizzes` (`QuizID`)
);
/*!40000 ALTER TABLE `QuizSubmissions` DISABLE KEYS */;
INSERT INTO `QuizSubmissions` VALUES ('uvchan','f3839cce-504e-4fff-83ff-c84bc16e8e1f','10',1,'FAIL','2024-03-12 09:26:42'),('uvchan','f3839cce-504e-4fff-83ff-c84bc16e8e1f','11',2,'PASS','2024-03-12 09:40:32');
/*!40000 ALTER TABLE `QuizSubmissions` ENABLE KEYS */;