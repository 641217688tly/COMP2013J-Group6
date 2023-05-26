-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: classroommanagement
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `classroom_course`
--

DROP TABLE IF EXISTS `classroom_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classroom_course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `classroom_id` int DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `classroom_id` (`classroom_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `classroom_course_ibfk_1` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`id`),
  CONSTRAINT `classroom_course_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classroom_course`
--

LOCK TABLES `classroom_course` WRITE;
/*!40000 ALTER TABLE `classroom_course` DISABLE KEYS */;
INSERT INTO `classroom_course` VALUES (2,2,2),(3,3,3),(4,1,4),(5,4,5),(6,5,6),(7,6,7),(8,3,8),(9,7,9),(10,8,10),(12,1,1),(13,1,11),(14,5,12),(15,7,13);
/*!40000 ALTER TABLE `classroom_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classrooms`
--

DROP TABLE IF EXISTS `classrooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classrooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` int NOT NULL,
  `floor` int NOT NULL,
  `capacity` enum('大','中','小') COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classrooms`
--

LOCK TABLES `classrooms` WRITE;
/*!40000 ALTER TABLE `classrooms` DISABLE KEYS */;
INSERT INTO `classrooms` VALUES (1,502,5,'大',1),(2,316,3,'中',1),(3,414,4,'大',1),(4,614,6,'大',1),(5,302,3,'大',1),(6,312,3,'中',1),(7,214,2,'大',1),(8,102,1,'大',1);
/*!40000 ALTER TABLE `classrooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `start_week` int NOT NULL,
  `end_week` int NOT NULL,
  `week_day` int NOT NULL,
  `schooltime` int NOT NULL,
  `detail` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'数据结构与算法II Data Structs & Algs II (S/E)/COMP2014J',1,15,1,1,'LILLIS David John/2021软件工程1班;2021软件工程2班;2021软件工程3班'),(2,'特殊用途英语 English for Spec Acad Purposes/BDIC2007J',1,16,2,1,'Jessica Black/2021软件工程3班'),(3,'计算机网络 Computer Networks (S/E)/COMP2009J',3,15,2,2,'Alzubair Hassan/2021软件工程1班;2021软件工程2班;2021软件工程3班(Week3开始lab)'),(4,'数据库和信息系统 Databases and Info Sys (S/E)/COMP2013J',1,15,2,3,'Dong Ruihai/2021软件工程1班;2021软件工程2班;2021软件工程3班(face to face)'),(5,'计算机网络 Computer Networks (S/E)/COMP2009J',1,15,3,1,'Alzubair Hassan/2021软件工程1班;2021软件工程2班;2021软件工程3班(face to face)'),(6,'数据结构与算法II Data Structs & Algs II (S/E)/COMP2014J',3,15,3,2,'LILLIS David John/2021软件工程1班;2021软件工程2班;2021软件工程3班(lab)'),(7,'特殊用途英语 English for Spec Acad Purposes/BDIC2007J',1,16,4,3,'Jessica Black/2021软件工程3班'),(8,'软件工程课设1 Software EngProject 1 (B)/COMP2008J',3,15,4,4,'Alzubair Hassan/2021软件工程1班;2021软件工程2班;2021软件工程3班(lab)'),(9,'软件工程课设1 Software EngProject 1 (B)/COMP2008J',1,15,5,1,'Alzubair Hassan/2021软件工程1班;2021软件工程2班;2021软件工程3班(face to face)'),(10,'离散数学 Discrete Mathematics/BDIC2002J',1,16,5,4,'王少帆/2021软件工程1班;2021软件工程2班;2021软件工程3班'),(11,'数据库和信息系统 Databases and Info Sys (S/E)/COMP2013J',1,15,2,4,'Dong Ruihai/2021软件工程1班;2021软件工程2班;2021软件工程3班(lab)'),(12,'中级宏观经济学 Intermediate Macroeconomics/ECON2002J',2,14,2,5,'Fabrice Kampfen/2021金融学1班;2021金融学2班;2021金融学3班;2021金融学4班'),(13,'软件工程课设2 Software Engineering Project/COMP3030J',1,15,1,2,'BECKER Brett Arthur,MOONEY Catherine Louise/2020软件工程1班;2020软件工程2班;2020软件工程3班');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservations`
--

DROP TABLE IF EXISTS `reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `classroom_id` int DEFAULT NULL,
  `week` int NOT NULL,
  `week_day` int NOT NULL,
  `schooltime` int NOT NULL,
  `purpose` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `classroom_id` (`classroom_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`id`),
  CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservations`
--

LOCK TABLES `reservations` WRITE;
/*!40000 ALTER TABLE `reservations` DISABLE KEYS */;
INSERT INTO `reservations` VALUES (1,3,1,1,1,2,'Scheduled functional testing'),(2,3,1,1,7,1,'	 Scheduled functional testing'),(3,3,3,1,6,3,'Reservation function attempt'),(4,3,5,1,7,1,'Scheduled functional testing'),(5,1,1,18,7,1,'Reservation function attempt'),(6,1,1,18,7,6,'Reservation function attempt'),(7,3,1,1,6,1,'test');
/*!40000 ALTER TABLE `reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('administrator','user') COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'liyantao','20030207TLY','641217688@qq.com','user'),(2,'zhangsan','1234','zhangsan@qq.com','user'),(3,'Administrator','Administrator','Administrator@qq.com','administrator');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-26 17:13:52
