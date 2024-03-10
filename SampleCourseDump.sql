-- MySQL dump 10.13  Distrib 5.7.42, for Linux (x86_64)
--
-- Host: localhost    Database: course
-- ------------------------------------------------------
-- Server version	5.7.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CourseQuestions`
--

DROP TABLE IF EXISTS `CourseQuestions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CourseQuestions` (
  `levelID` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `description` text NOT NULL,
  `code` text NOT NULL,
  PRIMARY KEY (`levelID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CourseQuestions`
--

LOCK TABLES `CourseQuestions` WRITE;
/*!40000 ALTER TABLE `CourseQuestions` DISABLE KEYS */;
INSERT INTO `CourseQuestions` VALUES (1,'Hello, Java!','Hello ninja, in this lesson you will learn how to use Scanner, Import scanner from the java library using the import statement import Java.util.scanner then get input from user and oprint the input of the user','public class HelloWorld{\n//Declare and string and assign \"Hello Java to it and print it  \"}'),(2,'Introduction to User Input in Java','Hey ninja Welcome to the exciting world of Java programming, where you start interacting with users! In this lesson, you will learn the essential steps to set up your Java program for accepting user input. Your task is to import the Scanner class, which is a crucial part of Java\'s utility library, allowing for reading inputs from various sources, including the user\'s keyboard. You\'ll be provided with a class template where you\'ll write the code to ask for user input and process it. Remember, the key to learning programming is experimentation and practice. So, dive in, and don\'t hesitate to try different approaches to solve the task.','public class UserInputDemo {\n    public static void main(String[] args) {\n        // Your code here: Start by importing the java.util.Scanner;\n        // Next, create a Scanner object to read input from System.in;\n        // Finally, prompt the user for their input, read it using the Scanner, and print it.\n    }\n}'),(3,'Squaring a Number: User Input in Java','Understanding user input and basic arithmetic operations are foundational skills in Java programming. In this level, you will combine these two skills by writing a program that prompts the user to enter an integer, then calculates and prints the square of that number. This exercise will reinforce your understanding of the Scanner class for reading user input, and demonstrate how to apply arithmetic operations to user-provided data.','public class SquareNumber {\n    public static void main(String[] args) {\n        // Import the Scanner class\n        // Create a Scanner object\n        // Prompt the user to enter a number\n        // Read the user input\n        // Calculate the square of the input\n        // Print the result\n    }\n}');
/*!40000 ALTER TABLE `CourseQuestions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-10  9:45:32
