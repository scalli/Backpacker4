CREATE DATABASE  IF NOT EXISTS `backpacker1` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `backpacker1`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: backpacker1
-- ------------------------------------------------------
-- Server version	5.6.19

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
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `album` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ID_USER` bigint(20) NOT NULL,
  `ALBUMNAME` varchar(100) NOT NULL,
  `COMMENT` longtext,
  `DATEALBUM` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ALBUM_USER` (`ID_USER`),
  CONSTRAINT `FK_ALBUM_USER` FOREIGN KEY (`ID_USER`) REFERENCES `appuser` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `album_photo`
--

DROP TABLE IF EXISTS `album_photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `album_photo` (
  `ID_ALBUM` bigint(20) NOT NULL,
  `ID_PHOTO` bigint(20) NOT NULL,
  PRIMARY KEY (`ID_ALBUM`,`ID_PHOTO`),
  KEY `FK_ALBUM_PHOTO_PHOTO` (`ID_PHOTO`),
  CONSTRAINT `FK_ALBUM_PHOTO_ALBUM` FOREIGN KEY (`ID_ALBUM`) REFERENCES `album` (`ID`),
  CONSTRAINT `FK_ALBUM_PHOTO_PHOTO` FOREIGN KEY (`ID_PHOTO`) REFERENCES `photo` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album_photo`
--

LOCK TABLES `album_photo` WRITE;
/*!40000 ALTER TABLE `album_photo` DISABLE KEYS */;
/*!40000 ALTER TABLE `album_photo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appuser`
--

DROP TABLE IF EXISTS `appuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appuser` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FIRSTNAME` varchar(100) NOT NULL,
  `LASTNAME` varchar(100) NOT NULL,
  `EMAIL` varchar(150) NOT NULL,
  `USERNAME` varchar(50) NOT NULL,
  `USERPASSWORD` varchar(50) NOT NULL,
  `ID_PHOTO` bigint(20) DEFAULT NULL,
  `ID_POSITION` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `USERNAME_UK` (`USERNAME`),
  KEY `FK_APPUSER_PHOTO` (`ID_PHOTO`),
  KEY `FK_APPUSER_POSITION` (`ID_POSITION`),
  CONSTRAINT `FK_APPUSER_PHOTO` FOREIGN KEY (`ID_PHOTO`) REFERENCES `photo` (`ID`) ON UPDATE NO ACTION,
  CONSTRAINT `FK_APPUSER_POSITION` FOREIGN KEY (`ID_POSITION`) REFERENCES `position` (`ID`) ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appuser`
--

LOCK TABLES `appuser` WRITE;
/*!40000 ALTER TABLE `appuser` DISABLE KEYS */;
INSERT INTO `appuser` VALUES (1,'user','user','user@mail.com','user','user',1,1),(2,'admin','admin','admin@mail.com','admin','admin',2,2);
/*!40000 ALTER TABLE `appuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ID_USER` bigint(20) NOT NULL,
  `ID_TYPEINFO` bigint(20) NOT NULL,
  `ID_POSITION` bigint(20) NOT NULL,
  `COMMENT` longtext,
  `DATEFEEDBACK` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_FEEDBACK_POSITION` (`ID_POSITION`),
  KEY `FK_FEEDBACK_USER_idx` (`ID_USER`),
  KEY `FK_FEEDBACK_TYPEINFO_idx` (`ID_TYPEINFO`),
  CONSTRAINT `FK_FEEDBACK_POSITION` FOREIGN KEY (`ID_POSITION`) REFERENCES `position` (`ID`),
  CONSTRAINT `FK_FEEDBACK_TYPEINFO` FOREIGN KEY (`ID_TYPEINFO`) REFERENCES `typeinfo` (`ID`),
  CONSTRAINT `FK_FEEDBACK_USER` FOREIGN KEY (`ID_USER`) REFERENCES `appuser` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback_photo`
--

DROP TABLE IF EXISTS `feedback_photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback_photo` (
  `ID_FEEDBACK` bigint(20) NOT NULL,
  `ID_PHOTO` bigint(20) NOT NULL,
  PRIMARY KEY (`ID_FEEDBACK`,`ID_PHOTO`),
  KEY `FK_FEEDBACK_PHOTO_PHOTO` (`ID_PHOTO`),
  CONSTRAINT `FK_FEEDBACK_PHOTO_FEEDBACK` FOREIGN KEY (`ID_FEEDBACK`) REFERENCES `feedback` (`ID`),
  CONSTRAINT `FK_FEEDBACK_PHOTO_PHOTO` FOREIGN KEY (`ID_PHOTO`) REFERENCES `photo` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback_photo`
--

LOCK TABLES `feedback_photo` WRITE;
/*!40000 ALTER TABLE `feedback_photo` DISABLE KEYS */;
/*!40000 ALTER TABLE `feedback_photo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photo`
--

DROP TABLE IF EXISTS `photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `photo` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(100) DEFAULT NULL,
  `ID_POSITION` bigint(20) NOT NULL,
  `DATETAKEN` datetime DEFAULT NULL,
  `COMMENT` longtext,
  `FULLPHOTO` varchar(1000) NOT NULL,
  `THUMBNAIL` varchar(1000) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PHOTO_POSITION` (`ID_POSITION`),
  CONSTRAINT `FK_PHOTO_POSITION` FOREIGN KEY (`ID_POSITION`) REFERENCES `position` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photo`
--

LOCK TABLES `photo` WRITE;
/*!40000 ALTER TABLE `photo` DISABLE KEYS */;
INSERT INTO `photo` VALUES (1,'description photo 1',1,'2015-10-27 00:00:00','comment','photoURL','thumbnailURL'),(2,'description photo 2',2,'2015-10-28 00:00:00','comment photo 2','url photo 2','url thumbnail 2');
/*!40000 ALTER TABLE `photo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `LATITUDE` decimal(12,9) DEFAULT NULL,
  `LONGITUDE` decimal(12,9) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (1,12.125236987,18.123456789),(2,123.123456789,312.123456789);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typeinfo`
--

DROP TABLE IF EXISTS `typeinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `typeinfo` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typeinfo`
--

LOCK TABLES `typeinfo` WRITE;
/*!40000 ALTER TABLE `typeinfo` DISABLE KEYS */;
INSERT INTO `typeinfo` VALUES (1,'Restaurant'),(2,'Café/Bar'),(3,'Hotel'),(4,'Bakery'),(5,'Local customs'),(6,'Transport'),(7,'Historical centre'),(8,'Nature reserves'),(99999,'Other');
/*!40000 ALTER TABLE `typeinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `iduser_roles` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(45) NOT NULL,
  `USERROLE` varchar(45) NOT NULL,
  PRIMARY KEY (`iduser_roles`),
  KEY `USERNAME_idx` (`USERNAME`),
  CONSTRAINT `USERNAME` FOREIGN KEY (`USERNAME`) REFERENCES `appuser` (`USERNAME`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'user','ROLE_USER'),(2,'admin','ROLE_ADMIN');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-28 22:37:57
