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
INSERT INTO `CourseQuestions` VALUES (1,'Question 1','Write a Java program to calculate the factorial of a given number.','public class Main {\n\n	public static void main(String[] args) {\n		// Write your Java solution code here\n	}\n}'),(2,'Question 2','Write a Java program to check if a given string is a palindrome or not.','public class Main {\n\n	public static void main(String[] args) {\n		// Write your Java solution code here\n	}\n}'),(3,'Question 3','Write a Java program to find the maximum and minimum elements in an array.','public class Main {\n\n	public static void main(String[] args) {\n		// Write your Java solution code here\n	}\n}');
/*!40000 ALTER TABLE `CourseQuestions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CourseTestcases`
--

DROP TABLE IF EXISTS `CourseTestcases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CourseTestcases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL,
  `input_value` varchar(255) DEFAULT NULL,
  `expected_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `question_id` (`question_id`),
  CONSTRAINT `CourseTestcases_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `CourseQuestions` (`levelID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CourseTestcases`
--

LOCK TABLES `CourseTestcases` WRITE;
/*!40000 ALTER TABLE `CourseTestcases` DISABLE KEYS */;
INSERT INTO `CourseTestcases` VALUES (1,1,'5','120'),(2,1,'0','1'),(3,1,'10','3628800'),(4,2,'racecar','true'),(5,2,'hello','false'),(6,2,'madam','true'),(7,3,'[1, 2, 3, 4, 5]','[1, 5]'),(8,3,'[10, 5, 3, 8, 15]','[3, 15]'),(9,3,'[-1, -5, -3, -8, -15]','[-15, -1]');
/*!40000 ALTER TABLE `CourseTestcases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QCheckerRelation`
--

DROP TABLE IF EXISTS `QCheckerRelation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QCheckerRelation` (
  `levelID` int(11) DEFAULT NULL,
  `checkerID` tinyint(4) DEFAULT NULL,
  KEY `levelID` (`levelID`),
  KEY `checkerID` (`checkerID`),
  CONSTRAINT `QCheckerRelation_ibfk_1` FOREIGN KEY (`levelID`) REFERENCES `CourseQuestions` (`levelID`),
  CONSTRAINT `QCheckerRelation_ibfk_2` FOREIGN KEY (`checkerID`) REFERENCES `checkers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QCheckerRelation`
--

LOCK TABLES `QCheckerRelation` WRITE;
/*!40000 ALTER TABLE `QCheckerRelation` DISABLE KEYS */;
INSERT INTO `QCheckerRelation` VALUES (1,5),(2,5),(3,5);
/*!40000 ALTER TABLE `QCheckerRelation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserCurrentLevel`
--

DROP TABLE IF EXISTS `UserCurrentLevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserCurrentLevel` (
  `username` varchar(50) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  KEY `username` (`username`),
  KEY `level` (`level`),
  CONSTRAINT `UserCurrentLevel_ibfk_1` FOREIGN KEY (`username`) REFERENCES `Users` (`username`),
  CONSTRAINT `UserCurrentLevel_ibfk_2` FOREIGN KEY (`level`) REFERENCES `CourseQuestions` (`levelID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserCurrentLevel`
--

LOCK TABLES `UserCurrentLevel` WRITE;
/*!40000 ALTER TABLE `UserCurrentLevel` DISABLE KEYS */;
INSERT INTO `UserCurrentLevel` VALUES ('uvchan',2);
/*!40000 ALTER TABLE `UserCurrentLevel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UserSubmission`
--

DROP TABLE IF EXISTS `UserSubmission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserSubmission` (
  `submissionID` int(11) NOT NULL AUTO_INCREMENT,
  `levelId` int(11) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `code` text,
  `submissionDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`submissionID`),
  KEY `levelId` (`levelId`),
  KEY `username` (`username`),
  CONSTRAINT `UserSubmission_ibfk_1` FOREIGN KEY (`levelId`) REFERENCES `CourseQuestions` (`levelID`),
  CONSTRAINT `UserSubmission_ibfk_2` FOREIGN KEY (`username`) REFERENCES `Users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserSubmission`
--

LOCK TABLES `UserSubmission` WRITE;
/*!40000 ALTER TABLE `UserSubmission` DISABLE KEYS */;
INSERT INTO `UserSubmission` VALUES (4,1,'uvchan','yguygsi','2024-02-28 08:57:42');
/*!40000 ALTER TABLE `UserSubmission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `username` varchar(64) NOT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `firstname` varchar(64) DEFAULT NULL,
  `lastname` varchar(64) DEFAULT NULL,
  `isVerified` enum('true','false') NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('uvchan','uvchan@gmail.com','uvchan123','magesh','waran','true');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkers`
--

DROP TABLE IF EXISTS `checkers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `checkers` (
  `id` tinyint(4) NOT NULL,
  `name` varchar(25) NOT NULL,
  `class_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkers`
--

LOCK TABLES `checkers` WRITE;
/*!40000 ALTER TABLE `checkers` DISABLE KEYS */;
INSERT INTO `checkers` VALUES (1,'Naming convention','NamingConventionChecker'),(2,'Inheritance','InheritanceChecker'),(3,'Polymorphism','PolymorphismChecker'),(4,'Encapsulation','EncapsulationChecker'),(5,'Logic','logic');
/*!40000 ALTER TABLE `checkers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

<<<<<<< HEAD
-- Dump completed on 2024-02-29 10:45:29
=======
-- Dump completed on 2024-02-29 11:04:44
>>>>>>> d38b0c4965394aac8dce68fecf3090609fd86754
