LOCK TABLES `Quizzes` WRITE;
/*!40000 ALTER TABLE `Quizzes` DISABLE KEYS */;
INSERT INTO `Quizzes` VALUES ('2b38fdf9-d3d3-4158-b55e-b1481912b284','Intro to Java','MCQ',5,'2024-03-13 19:11:29','2024-03-13 19:11:29'),('871fea4c-c823-4c19-ae74-4619347f389b','Control Flow','MCQ',7,'2024-03-13 19:52:14','2024-03-13 19:52:14'),('fb203cb9-c591-4824-bbd3-85d56510f92c','Strings in Java','MCQ',6,'2024-03-13 19:26:59','2024-03-13 19:26:59');
/*!40000 ALTER TABLE `Quizzes` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `QuizQuestions` WRITE;
/*!40000 ALTER TABLE `QuizQuestions` DISABLE KEYS */;
INSERT INTO `QuizQuestions` VALUES ('2b38fdf9-d3d3-4158-b55e-b1481912b284','q1','What is Java?','o1'),('871fea4c-c823-4c19-ae74-4619347f389b','q1','What is the purpose of the \"if\" statement in Java?','o3'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q1','What is the result of the expression \"Hello\".length()?','o1'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q2','Which keyword is used to define a class in Java?','o2'),('871fea4c-c823-4c19-ae74-4619347f389b','q2','Which loop in Java will always execute at least once?','o1'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q2','Which method is used to concatenate two strings in Java?','o4'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q3','Which of the following is a correct way to declare and initialize a String variable in Java?','o3'),('871fea4c-c823-4c19-ae74-4619347f389b','q3','What is the result of the expression: !(5 > 3 && 4 < 2)?','o2'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q3','What does the indexOf() method in Java return if the substring is not found in the given string?','o2'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q4','Which of the following data types is used to store whole numbers in Java?','o3'),('871fea4c-c823-4c19-ae74-4619347f389b','q4','What happens when the \"break\" statement is encountered inside a loop?','o3'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q4','Which of the following methods is used to convert all characters in a string to lowercase?','o2'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q5','What is the result of the expression: 10 > 5 && 5 < 3?','o2'),('871fea4c-c823-4c19-ae74-4619347f389b','q5','What is the purpose of the \"else\" statement in Java\'s \"if-else\" construct?','o1'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q5','What does the substring() method in Java do?','o4'),('871fea4c-c823-4c19-ae74-4619347f389b','q6','What does the \"default\" case in a switch statement represent?','o2'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q6','Which of the following methods is used to remove leading and trailing white spaces from a string in Java?','o3'),('871fea4c-c823-4c19-ae74-4619347f389b','q7','What is the purpose of the \"continue\" statement in Java?','o2');
/*!40000 ALTER TABLE `QuizQuestions` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `Options` WRITE;
/*!40000 ALTER TABLE `Options` DISABLE KEYS */;
INSERT INTO `Options` VALUES ('2b38fdf9-d3d3-4158-b55e-b1481912b284','q1','o1','A programming language','true'),('871fea4c-c823-4c19-ae74-4619347f389b','q1','o1','To define a loop.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q1','o1','5','true'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q2','o1','struct','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q2','o1','do-while loop','true'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q2','o1','add()','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q3','o1','str = \"Hello\";','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q3','o1','false','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q3','o1','0','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q4','o1','double','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q4','o1','It continues to the next iteration of the loop.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q4','o1','toUppercase()','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q5','o1','true','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q5','o1','To execute a block of code if the condition in the \"if\" statement is false.','true'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q5','o1','Returns a substring from the beginning of the given string','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q6','o1','It is the first case to be executed in a switch statement.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q6','o1','removeSpaces()','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q7','o1','It exits the loop immediately and resumes execution at the next statement after the loop.','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q1','o2','A coffee brand','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q1','o2','To declare a method.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q1','o2','6','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q2','o2','class','true'),('871fea4c-c823-4c19-ae74-4619347f389b','q2','o2','for loop','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q2','o2','append()','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q3','o2','string str = \"Hello\";','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q3','o2','true','true'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q3','o2','-1','true'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q4','o2','char','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q4','o2','It throws an exception.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q4','o2','toLowerCase()','true'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q5','o2','false','true'),('871fea4c-c823-4c19-ae74-4619347f389b','q5','o2','To define a loop.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q5','o2','Returns a substring from the end of the given string','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q6','o2','It represents the case where no other case matches the switch expression.','true'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q6','o2','clean()','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q7','o2','It skips the remaining code in the current iteration of the loop and continues with the next iteration.','true'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q1','o3','A type of car','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q1','o3','To execute a block of code if a condition is true.','true'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q1','o3','4','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q2','o3','interface','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q2','o3','while loop','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q2','o3','plus()','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q3','o3','String str = \"Hello\"','true'),('871fea4c-c823-4c19-ae74-4619347f389b','q3','o3','Compile error','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q3','o3','1','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q4','o3','int','true'),('871fea4c-c823-4c19-ae74-4619347f389b','q4','o3','It exits the loop immediately and resumes execution at the next statement after the loop.','true'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q4','o3','convertToLowerCase()','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q5','o3','Compile error','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q5','o3','To declare a method.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q5','o3','Returns a substring from the middle of the given string','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q6','o3','It is executed if the switch expression is null.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q6','o3','trim()','true'),('871fea4c-c823-4c19-ae74-4619347f389b','q7','o3','It throws an exception.','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q1','o4','An operating system','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q1','o4','To handle exceptions.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q1','o4','0','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q2','o4','object','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q2','o4','switch statement','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q2','o4','concat()','true'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q3','o4','str Hello = new String();','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q3','o4','Runtime error','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q3','o4','Throws an exception','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q4','o4','boolean','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q4','o4','It skips the next iteration of the loop.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q4','o4','lowerCase()','false'),('2b38fdf9-d3d3-4158-b55e-b1481912b284','q5','o4','Runtime error','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q5','o4','To handle exceptions.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q5','o4','Returns a substring based on the specified indices','true'),('871fea4c-c823-4c19-ae74-4619347f389b','q6','o4','It is optional and can be omitted in a switch statement.','false'),('fb203cb9-c591-4824-bbd3-85d56510f92c','q6','o4','strip()','false'),('871fea4c-c823-4c19-ae74-4619347f389b','q7','o4','It jumps to a specific label in the code.','false');
/*!40000 ALTER TABLE `Options` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `QuizSubmissions` WRITE;
/*!40000 ALTER TABLE `QuizSubmissions` DISABLE KEYS */;
INSERT INTO `QuizSubmissions` VALUES ('uvchan','2b38fdf9-d3d3-4158-b55e-b1481912b284','01000',1,'FAIL','2024-03-13 19:55:05'),('uvchan','871fea4c-c823-4c19-ae74-4619347f389b','0011101',4,'FAIL','2024-03-13 19:55:44');
/*!40000 ALTER TABLE `QuizSubmissions` ENABLE KEYS */;
UNLOCK TABLES;

