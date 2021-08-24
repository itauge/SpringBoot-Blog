-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: blog
-- ------------------------------------------------------
-- Server version	8.0.24

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
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` (`next_val`) VALUES (43);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_blog`
--

DROP TABLE IF EXISTS `t_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_blog` (
  `id` bigint NOT NULL,
  `appreciation` bit(1) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `first_picture` varchar(255) DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `recommend` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `views` int DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `comment` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `publish` bit(1) NOT NULL,
  `share_state` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK292449gwg5yf7ocdlmswv9w4j` (`type_id`),
  KEY `FK8ky5rrsxh01nkhctmo7d48p82` (`user_id`),
  CONSTRAINT `FK292449gwg5yf7ocdlmswv9w4j` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`),
  CONSTRAINT `FK8ky5rrsxh01nkhctmo7d48p82` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog`
--

LOCK TABLES `t_blog` WRITE;
/*!40000 ALTER TABLE `t_blog` DISABLE KEYS */;
INSERT INTO `t_blog` (`id`, `appreciation`, `content`, `create_time`, `first_picture`, `flag`, `recommend`, `title`, `update_time`, `views`, `type_id`, `user_id`, `comment`, `description`, `publish`, `share_state`) VALUES (1,_binary '',' halo','2021-08-16 15:00:28.000000',NULL,NULL,_binary '','halo','2021-08-16 15:00:49.000000',3,1,1,_binary '\0','1',_binary '\0',_binary '\0'),(2,_binary '',' x','2021-08-16 15:00:28.000000',NULL,NULL,_binary '','x','2021-08-16 15:00:49.000000',2,2,1,_binary '\0','1',_binary '\0',_binary '\0'),(34,_binary '\0','## 12331','2021-08-17 14:14:31.563000','/static/images/b1.png','原創',_binary '','testing','2021-08-17 14:14:31.563000',6,4,1,_binary '\0','1',_binary '',_binary '\0');
/*!40000 ALTER TABLE `t_blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_blog_tags`
--

DROP TABLE IF EXISTS `t_blog_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_blog_tags` (
  `blogs_id` bigint NOT NULL,
  `tags_id` bigint NOT NULL,
  KEY `FK5feau0gb4lq47fdb03uboswm8` (`tags_id`),
  KEY `FKh4pacwjwofrugxa9hpwaxg6mr` (`blogs_id`),
  CONSTRAINT `FK5feau0gb4lq47fdb03uboswm8` FOREIGN KEY (`tags_id`) REFERENCES `t_tag` (`id`),
  CONSTRAINT `FKh4pacwjwofrugxa9hpwaxg6mr` FOREIGN KEY (`blogs_id`) REFERENCES `t_blog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_blog_tags`
--

LOCK TABLES `t_blog_tags` WRITE;
/*!40000 ALTER TABLE `t_blog_tags` DISABLE KEYS */;
INSERT INTO `t_blog_tags` (`blogs_id`, `tags_id`) VALUES (1,1),(1,2),(1,3),(34,1),(34,3);
/*!40000 ALTER TABLE `t_blog_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_comment` (
  `id` bigint NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `blog_id` bigint DEFAULT NULL,
  `parent_comment_id` bigint DEFAULT NULL,
  `admin` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKke3uogd04j4jx316m1p51e05u` (`blog_id`),
  KEY `FK4jj284r3pb7japogvo6h72q95` (`parent_comment_id`),
  CONSTRAINT `FK4jj284r3pb7japogvo6h72q95` FOREIGN KEY (`parent_comment_id`) REFERENCES `t_comment` (`id`),
  CONSTRAINT `FKke3uogd04j4jx316m1p51e05u` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_comment`
--

LOCK TABLES `t_comment` WRITE;
/*!40000 ALTER TABLE `t_comment` DISABLE KEYS */;
INSERT INTO `t_comment` (`id`, `avatar`, `content`, `create_time`, `email`, `nickname`, `blog_id`, `parent_comment_id`, `admin`) VALUES (37,'/static/images/head.png','3','2021-08-19 14:19:43.094000','123@gmail.com','123',34,NULL,_binary '\0'),(38,'/static/images/head.png','123','2021-08-19 14:27:02.080000','123@gmail.com','123',34,NULL,_binary '\0'),(39,'/static/images/head.png','teset reply 123','2021-08-19 14:52:30.905000','123@gmail.com','456',34,38,_binary '\0'),(40,'/static/images/head.png','test reply 456','2021-08-19 15:43:24.524000','123@gmail.com','789',34,39,_binary '\0');
/*!40000 ALTER TABLE `t_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_tag`
--

DROP TABLE IF EXISTS `t_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_tag` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_tag`
--

LOCK TABLES `t_tag` WRITE;
/*!40000 ALTER TABLE `t_tag` DISABLE KEYS */;
INSERT INTO `t_tag` (`id`, `name`) VALUES (1,'test1'),(2,'he'),(3,'hi'),(42,'halo');
/*!40000 ALTER TABLE `t_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_type`
--

DROP TABLE IF EXISTS `t_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_type` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_type`
--

LOCK TABLES `t_type` WRITE;
/*!40000 ALTER TABLE `t_type` DISABLE KEYS */;
INSERT INTO `t_type` (`id`, `name`) VALUES (1,'123'),(2,'345'),(3,'het'),(4,'springboot'),(6,'test'),(7,'be'),(9,'halo');
/*!40000 ALTER TABLE `t_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`id`, `avatar`, `create_time`, `email`, `nickname`, `password`, `type`, `update_time`, `username`) VALUES (1,'/static/images/adminIcon.jpg','2021-08-15 14:45:57.000000','admin@123.com','admin','21232f297a57a5a743894a0e4a801fc3',NULL,'2021-08-15 14:46:14.000000','admin');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-24 16:49:19
