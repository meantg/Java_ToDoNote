-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: dttodo
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notes` (
  `noteID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `categoryID` int(11) DEFAULT NULL,
  `title` longtext NOT NULL,
  `description` longtext,
  `stateID` int(11) DEFAULT NULL,
  `isMyDay` tinyint(4) DEFAULT NULL,
  `isImportance` tinyint(4) DEFAULT NULL,
  `createDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `dueDate` datetime DEFAULT NULL,
  PRIMARY KEY (`noteID`),
  KEY `userID_idx` (`userID`),
  KEY `categoryID_idx` (`categoryID`),
  KEY `stateID_note_idx` (`stateID`),
  CONSTRAINT `categoryID` FOREIGN KEY (`categoryID`) REFERENCES `categories` (`CategoryID`) ON DELETE CASCADE,
  CONSTRAINT `stateID_note` FOREIGN KEY (`stateID`) REFERENCES `state` (`stateID`),
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13029 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
INSERT INTO `notes` VALUES (13020,11001,11025,'ehehe','',12002,0,1,'2020-01-03 00:00:00',NULL),(13021,11001,11025,'abc','',12001,0,1,'2020-01-03 00:00:00',NULL);
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-03 17:01:31
