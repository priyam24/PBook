-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: hello
-- ------------------------------------------------------
-- Server version	8.0.11

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
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follow` (
  `email1` varchar(50) DEFAULT NULL,
  `email2` varchar(50) DEFAULT NULL,
  KEY `email1` (`email1`),
  KEY `email2` (`email2`),
  CONSTRAINT `follow_ibfk_1` FOREIGN KEY (`email1`) REFERENCES `user` (`email`),
  CONSTRAINT `follow_ibfk_2` FOREIGN KEY (`email2`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES ('priyam.roy1@wipro.com','abc@google.com'),('aryan@apple.com','sam@usfoods.com'),('manbir@google.com','aryan@apple.com'),('manbir@google.com','priyam.roy1@wipro.com'),('abc@google.com','sam@usfoods.com'),('priyam.roy1@wipro.com','sam@usfoods.com'),('manbir@google.com','srk@redchillies.com'),('priyam.roy1@wipro.com','srk@redchillies.com'),('sayantan@fb.com','sam@usfoods.com'),('sayantan@fb.com','srk@redchillies.com'),('abc@google.com','priyam.roy1@wipro.com'),('abc@google.com','aryan@apple.com'),('mili@wipro.com','aryan@apple.com'),('mili@wipro.com','priyam.roy1@wipro.com'),('mili@wipro.com','srk@redchillies.com'),('munna@yahoo.com','srk@redchillies.com'),('munna@yahoo.com','priyam.roy1@wipro.com'),('priyam.roy1@wipro.com','mili@wipro.com'),('protis3@gmail.com','sam@usfoods.com');
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-05 19:21:32
