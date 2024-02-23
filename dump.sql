-- MySQL dump 10------------------
-- Server version	5.7.42

/*!40101 SET @OLD_CHARACTER_SET_.13  Distrib 5.7.42, for Linux (x86_64)
--
-- Host: localhost    Database: course
-- ------------------------------------CLIENT=@@CHARACTER_SET_CLIENT */;
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-23 16:20:56
