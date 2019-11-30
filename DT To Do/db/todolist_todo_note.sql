-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: todolist
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
-- Table structure for table `todo_note`
--

DROP TABLE IF EXISTS `todo_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `todo_note` (
  `MaNote` int(11) NOT NULL AUTO_INCREMENT,
  `MaPhanLoai` int(11) NOT NULL,
  `TieuDe` varchar(45) NOT NULL,
  `NoiDung` longtext,
  `MaTinhTrang` int(11) NOT NULL,
  `NgayTao` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `HanChot` datetime DEFAULT NULL,
  PRIMARY KEY (`MaNote`),
  KEY `fk_maphanloai_idx` (`MaPhanLoai`),
  CONSTRAINT `fk_maphanloai` FOREIGN KEY (`MaPhanLoai`) REFERENCES `phanloai` (`MaPhanLoai`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13107 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `todo_note`
--

LOCK TABLES `todo_note` WRITE;
/*!40000 ALTER TABLE `todo_note` DISABLE KEYS */;
INSERT INTO `todo_note` VALUES (13002,10001,'Đi học anh văn','alocaaasdasdasda',12001,'2019-11-13 08:56:45',NULL),(13003,10001,'ABC','XYzadkmlasasd',12001,'2019-11-14 00:25:52',NULL),(13004,10002,'Deadline 1','Flutter',12002,'2019-11-16 12:46:19',NULL),(13010,10001,'Greeting','Greeting 5 people at the partyasdasq',12002,'2019-11-26 10:50:41',NULL),(13013,10147,'Hello','',12002,'2019-11-26 14:58:36',NULL),(13063,10003,'abcedf','',12001,'2019-11-28 13:10:06',NULL),(13099,10001,'asdqwdqd','',12001,'2019-11-28 20:27:11',NULL),(13101,10001,'HTaoikjcla','Hello',12001,'2019-11-28 20:29:33',NULL),(13103,10001,'New Note','abc',12001,'2019-11-30 10:51:14',NULL),(13104,10161,'bhb','',12002,'2019-11-30 11:12:56',NULL),(13105,10002,'Deadline 2','Mobile 2 Week Practice ',12002,'2019-11-30 11:27:03',NULL),(13106,10002,'App TO DO','Fix bug',12001,'2019-11-30 14:44:16',NULL);
/*!40000 ALTER TABLE `todo_note` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-30 14:47:18
