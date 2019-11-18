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
-- Table structure for table `phanloai`
--

DROP TABLE IF EXISTS `phanloai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phanloai` (
  `MaPhanLoai` int(11) NOT NULL AUTO_INCREMENT,
  `MaNguoiDung` int(11) NOT NULL,
  `TenPhanLoai` varchar(45) NOT NULL,
  `Icon` varchar(5) NOT NULL,
  PRIMARY KEY (`MaPhanLoai`)
) ENGINE=InnoDB AUTO_INCREMENT=10025 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phanloai`
--

LOCK TABLES `phanloai` WRITE;
/*!40000 ALTER TABLE `phanloai` DISABLE KEYS */;
INSERT INTO `phanloai` VALUES (10001,11001,'To Do','?'),(10002,11001,'Work','?'),(10003,11001,'Groceries','?'),(10007,11002,'To Do','?'),(10008,11002,'Work','?'),(10009,11002,'Groceries','?'),(10010,11003,'To Do ','?'),(10011,11003,'Work','?'),(10012,11003,'Groceries','?'),(10013,11004,'To Do ','?'),(10014,11004,'Work','?'),(10015,11004,'Groceries','?'),(10016,11005,'To Do ','?'),(10017,11005,'Work','?'),(10018,11005,'Groceries','?'),(10019,11006,'To Do ','?'),(10020,11006,'Work','?'),(10021,11006,'Groceries','?'),(10022,11007,'To Do ','?'),(10023,11007,'Work','?'),(10024,11007,'Groceries','?');
/*!40000 ALTER TABLE `phanloai` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-18 15:30:05
