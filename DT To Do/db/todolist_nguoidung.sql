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
-- Table structure for table `nguoidung`
--

DROP TABLE IF EXISTS `nguoidung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguoidung` (
  `MaNguoiDung` int(11) NOT NULL AUTO_INCREMENT,
  `TenNguoiDung` varchar(45) NOT NULL,
  `TenDangNhap` varchar(45) NOT NULL,
  `MatKhau` varchar(45) NOT NULL,
  `GioiTinh` varchar(10) NOT NULL,
  `SoDienThoai` varchar(45) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`MaNguoiDung`)
) ENGINE=InnoDB AUTO_INCREMENT=11009 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguoidung`
--

LOCK TABLES `nguoidung` WRITE;
/*!40000 ALTER TABLE `nguoidung` DISABLE KEYS */;
INSERT INTO `nguoidung` VALUES (11001,'Dat Wang','datvt99','datvt99','Nam','0782369351','17520343@gm.uit.edu.vn'),(11002,'Thắng Mập','thangvm99','thangvm99','Nam','0379221432','1751041@gm.uit.edu.vn'),(11003,'Nguyễn Thị Tường Vy','vyntt99','vyntt99','Nữ','0376628667','vyntt99@gmail.com'),(11004,'Đinh Hoàng Nhi','nhidh99','nhidh99','Nam','0124356789','nhidh99@gmail.com'),(11005,'Phạm Trung Trường','truongpt99','truongpt99','Nam','1234567890','truongpt99@gmail.com'),(11006,'Vương Tuyết Nhung','vuongtuyetnhung','vuongtuyetnhung','Nữ','0782369351','vuongtuyetnhung@gmail.com'),(11008,'vuong thinh dat','thinhdat1999','dat123456789','Nam','0376628667','vuongthinhdat1@gmail.com');
/*!40000 ALTER TABLE `nguoidung` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `nguoidung_AFTER_INSERT` AFTER INSERT ON `nguoidung` FOR EACH ROW BEGIN
	INSERT INTO phanloai (MaNguoiDung, TenPhanLoai, Icon) VALUES (NEW.MaNguoiDung, 'To Do ', '📋');
	INSERT INTO phanloai (MaNguoiDung, TenPhanLoai, Icon) VALUES (NEW.MaNguoiDung, 'Work', '🏬');
	INSERT INTO phanloai (MaNguoiDung, TenPhanLoai, Icon) VALUES (NEW.MaNguoiDung, 'Groceries', '🛒');
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-30 14:47:18
