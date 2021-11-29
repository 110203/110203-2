-- MySQL dump 10.13  Distrib 5.7.36, for Win64 (x86_64)
--
-- Host: 35.194.169.145    Database: Gcp110203
-- ------------------------------------------------------
-- Server version	5.7.36-google-log

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- Table structure for table `exhibition`
--

DROP TABLE IF EXISTS `exhibition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exhibition` (
  `eNo` int(11) NOT NULL AUTO_INCREMENT,
  `memNo` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `eFile` text COLLATE utf8_unicode_ci,
  `eFile2D` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `eName` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `introdution` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `startTime` date DEFAULT NULL,
  `endTime` date DEFAULT NULL,
  `eImage` text COLLATE utf8_unicode_ci,
  `eType` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ePin` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '亂數由數字加字母加符號共10碼',
  `eDelete` int(255) DEFAULT '0' COMMENT '0=存在 1=刪除 預設=0',
  `login` int(11) DEFAULT '0',
  `eCheck` int(11) DEFAULT '0',
  `style` varchar(6) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`eNo`) USING BTREE,
  KEY `memNo` (`memNo`) USING BTREE,
  CONSTRAINT `memNo` FOREIGN KEY (`memNo`) REFERENCES `member` (`memno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exhibition`
--

LOCK TABLES `exhibition` WRITE;
/*!40000 ALTER TABLE `exhibition` DISABLE KEYS */;
INSERT INTO `exhibition` VALUES (11,'a22753516@gmail.com',NULL,'https://do649bnc9x4hqjt8vrtjca-on.drv.tw/0907-2/','四十四隻石詩子','高先生睽違2天，將再次在展現上為大家帶來收集2天的44位名家詩集作品。','2021-04-15','2022-04-30','1622301392150--石詩子.png','藝術','TdXDwj5e+W',1,1,6,'2D, 3D'),(35,'a22753516@gmail.com',NULL,'https://do649bnc9x4hqjt8vrtjca-on.drv.tw/0809/','瓷與釉-異國瓷器聯展','中國的瓷器文化淵遠流長，享譽國際，但不同國家受中國影響亦結合了當地特色... ...','2021-08-14','2022-09-15','1628912612558--未命名文件 (3)_page-0001.jpg','藝術','rfE6oH2LDU',0,0,6,'2D, 3D'),(36,'a22753516@gmail.com',NULL,NULL,'劉侒個人攝影展','集結20個日子的攝影作品，在展現上展現給大家。','2021-05-08','2022-05-29','1619867795832--head.jpeg','藝術','c9CpezAHnr',1,0,6,'3D'),(38,'aaaa22753516@kimo.com',NULL,'https://do649bnc9x4hqjt8vrtjca-on.drv.tw/0907/','《天使》','張小婷老師的個人雕塑展','2021-05-01','2022-06-05','1622302223766--original.jpg','藝術','CX30jfAbzA',1,0,6,'2D'),(39,'a22753516@gmail.com',NULL,'https://do649bnc9x4hqjt8vrtjca-on.drv.tw/%E6%A0%A1%E5%8F%B2%E9%A4%A8/','校史館','這裡有好多好多的包包，一定有你理想的那一款！','2021-05-14','2022-05-27','1622301679626--186558439_133221345450251_8341247570167271462_n.jpg','消費展','sAwygkXpUp',0,1,6,'2D'),(40,'a22753516@gmail.com',NULL,NULL,'體育用品展覽會','夏季最朝運動暨戶外用品展','2021-05-26','2022-05-29','1622301693570--193108873_479962279733113_6251069077220987556_n.jpg','商業','HRi0Lrgzkw',1,1,6,'2D'),(41,'a22753516@gmail.com',NULL,'https://hxf08tjc64aji461hoz2bq-on.drv.tw/0907/','花畫展','花畫展1117','2021-06-14','2022-06-28','1637115993462--玫瑰水彩復刻畫.jpg','書畫','y/2OHTwO5s',0,0,6,'2D'),(60,'a22753516@gmail.com',NULL,'https://hxf08tjc64aji461hoz2bq-on.drv.tw/0907-2/','朱銘展','人間系列 朱銘自1980年代中期開始，創作了一系列的運動員。不同於以往木質、陶作或水墨的《人間系列》，此五件作品運用了藝術家新的創作技法，以麻繩綑綁大片海綿塑型，再加以翻銅製成作品。藝術家透過綑綁的鬆緊度，突顯運動員在進行體操當下身體與四肢的肌肉表現，同時也透過海綿材質本身的柔軟性，在人物胸部、腰際與膝蓋等較大的身體轉折處展現高度的靈活和彈性。透過選用的材質特性，藝術家充分地展現運動員力量與柔軟度的完美結合。','2021-11-01','2021-12-31','1635778760203--太極-對招.png','藝術','zyEp+l2FF4',0,0,6,'2D, 3D'),(64,'a22753516@gmail.com',NULL,'https://www.twitch.tv/brothers_baseball','測試','測試2','2021-11-14','2021-12-04','1636881956752--erd_page-0001.jpg','測試','prGVM29W9M',1,0,0,'2D'),(65,'a22753516@gmail.com',NULL,'https://do649bnc9x4hqjt8vrtjca-on.drv.tw/%E6%A0%A1%E5%8F%B2%E9%A4%A8/','校史館','經由國立臺北商業大學校史館，帶你一睹百年風華。','2021-11-20','2021-12-10','1636881956752--erd_page-0001.jpg','歷史','prGVM2889M',1,0,6,'2D'),(66,'a22753516@gmail.com',NULL,'https://www.twitch.tv/brothers_baseball','測試2','測試2','2021-11-21','2021-11-30','1637495295779--睡蓮油彩復刻畫.jpg','測試2','m7xYSxdqXC',0,0,6,'2D, 3D');
/*!40000 ALTER TABLE `exhibition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goods` (
  `gNo` int(11) NOT NULL AUTO_INCREMENT,
  `eNo` int(11) NOT NULL,
  `gName` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `introdution` varchar(40) COLLATE utf8_unicode_ci NOT NULL,
  `gAmount` int(255) NOT NULL,
  `price` int(10) NOT NULL,
  `gImage2D` text COLLATE utf8_unicode_ci,
  `gDelete` int(255) NOT NULL DEFAULT '0' COMMENT '0=存在 1=刪除 預設=0',
  PRIMARY KEY (`gNo`) USING BTREE,
  KEY `eNo` (`eNo`) USING BTREE,
  CONSTRAINT `eNo` FOREIGN KEY (`eNo`) REFERENCES `exhibition` (`eNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (2,11,'高高手寫','模仿顏真卿字跡',6,1000,'1622301946678--189246490_515860969539761_8968653731130075956_n.jpg',0),(3,35,'花間一壺酒','描繪在花間有一壺酒的美麗景色',1,200,'1622302043230--188535089_775874539793802_7022886383053121498_n.jpg',0),(4,38,'小天使','小天使雕像',1,5000,'1622302119541--original.jpg',0),(5,11,'車子','法拉利',2,1000,NULL,0),(6,38,'花瓶','清朝的花瓶',5,400,'1622302286487--11f5eb896e0242ac110005.jpg',0),(7,36,'清晨','大家皆睡我獨醒',5,700,'1620450032212--下載.jpg',0),(8,36,'晚霞','夕陽跟日出好像都長一樣',16,250,'1620521000056--下載.jpg',1),(9,39,'難捨難分','我的第一個愛包',5,100,'1620794132160--cart.png',0),(10,40,'瑜珈球','say起司研究15年的第一顆瑜珈球，滿足各種需求',5,1000,'1622302199895--da58f1cdcbf9a9110a88acd70a4310ff.jpg',0),(11,36,'花瓶','花瓶',54,1000,'1637114389134--土耳其鈷藍色花瓶.png',0),(19,60,'人間系列-體操一號','人間系列-體操一號',1,500000,'1635779012110--人間系列-體操一號.png',0),(20,60,'人間系列-體操二號','人間系列-體操二號',0,500000,'1635779053321--人間系列-體操二號.png',0),(21,60,'人間系列-跨欄','人間系列-跨欄',1,500000,'1635779085450--人間系列-跨欄.png',0),(22,60,'人間系列-體操三號','人間系列-體操三號',1,500000,'1635779106805--人間系列-體操三號.png',0),(23,60,'人間系列-體操四號','人間系列-體操四號',5,1000,'1636881058514--人間系列-體操四號 .png',0),(24,60,'太極-對招','太極-對招',7,1200,'1636881135662--太極-對招.png',0),(25,39,'LL Bean外刷毛深藍包','LL Bean外刷毛深藍包',5,1000,'1637111318200--LL Bean外刷毛深藍包.png',0),(26,39,'marmot手提保冷袋','marmot手提保冷袋',10,1200,'1637111340286--marmot手提保冷袋.png',0),(27,39,'Marmot灰色菱格斜背小包','Marmot灰色菱格斜背小包',3,1500,'1637111356404--Marmot灰色菱格斜背小包.png',0),(28,39,'Marmot後杯棕色保冷袋','Marmot後杯棕色保冷袋',6,1300,'1637111375724--Marmot後杯棕色保冷袋.png',0),(29,39,'mono橡皮擦造型包','mono橡皮擦造型包',15,800,'1637111395285--mono橡皮擦造型包.png',0),(30,60,'人間系列-婦女','人間系列-婦女',4,2000,'1637111479247--人間系列-婦女.png',0),(31,60,'太極-單鞭下勢','太極-單鞭下勢',10,1800,'1637111500727--太極-單鞭下勢.png',0),(32,60,'太極-圓','太極-圓',5,800,'1637111516138--太極-圓.png',0),(33,60,'太極-對招之二','太極-對招之二',20,2000,'1637111534533--太極-對招之二.png',0),(34,35,'土耳其咖啡杯','土耳其咖啡杯',5,500,'1637111581572--土耳其咖啡杯.png',0),(35,35,'土耳其咖啡鍋','土耳其咖啡鍋',10,250,'1637111604655--土耳其咖啡鍋.png',0),(36,35,'土耳其鈷藍色花瓶','土耳其鈷藍色花瓶',17,650,'1637111627488--土耳其鈷藍色花瓶.png',0),(37,35,'水墨青蝦杯','水墨青蝦杯',12,300,'1637111644459--水墨青蝦杯.png',0),(38,35,'青花茶杯','青花茶杯',28,300,'1637111659929--青花茶杯.png',0),(39,35,'剎寂杯盤組','剎寂杯盤組',10,400,'1637111675225--剎寂杯盤組.png',0),(40,35,'剎寂茶壺','剎寂茶壺',10,500,'1637111693531--剎寂茶壺.png',0),(41,35,'素燒陶罐','素燒陶罐',20,800,'1637111708405--素燒陶罐.png',0),(42,35,'素燒墨釉醬料碟','素燒墨釉醬料碟',30,150,'1637111725034--素燒墨釉醬料碟.png',0),(43,35,'逐鹿中原青花碗','逐鹿中原青花碗',10,200,'1637111745048--逐鹿中原青花碗.png',0),(44,35,'楔型鈷蘭茶杯','楔型鈷蘭茶杯',20,250,'1637111761844--楔型鈷蘭茶杯.png',0),(45,35,'雙色釉水皿','雙色釉水皿',15,300,'1637111780360--雙色釉水皿.png',0),(46,41,'向日葵油彩復刻畫','向日葵油彩復刻畫',4,3000,'1637113451235--向日葵油彩復刻畫.jpg',0),(47,41,'玫瑰水彩復刻畫','玫瑰水彩復刻畫',8,3500,'1637113467348--玫瑰水彩復刻畫.jpg',0),(48,41,'桔梗水彩復刻畫','桔梗水彩復刻畫',10,5000,'1637113488701--桔梗水彩復刻畫.jpg',0),(49,41,'睡蓮水彩復刻畫','睡蓮水彩復刻畫',10,4500,'1637113504269--睡蓮水彩復刻畫.jpg',0),(50,41,'睡蓮油彩復刻畫','睡蓮油彩復刻畫',5,7000,'1637113520625--睡蓮油彩復刻畫.jpg',0),(51,41,'繡球水彩復刻畫','繡球水彩復刻畫',3,8000,'1637113536112--繡球水彩復刻畫.jpg',0);
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `memNo` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `memPassword` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `memName` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `level` int(255) NOT NULL DEFAULT '0' COMMENT '0=會員廠商 1=管理者 預設=0',
  `memDelete` int(255) NOT NULL DEFAULT '0' COMMENT '0=存在 1=刪除 預設=0',
  PRIMARY KEY (`memNo`) USING BTREE,
  KEY `memNo` (`memNo`) USING BTREE,
  KEY `memNo_2` (`memNo`) USING BTREE,
  KEY `memNo_3` (`memNo`) USING BTREE,
  KEY `memNo_4` (`memNo`) USING BTREE,
  KEY `memNo_5` (`memNo`) USING BTREE,
  KEY `memNo_6` (`memNo`) USING BTREE,
  KEY `memNo_7` (`memNo`) USING BTREE,
  KEY `memNo_8` (`memNo`) USING BTREE,
  KEY `memNo_9` (`memNo`) USING BTREE,
  KEY `memNo_10` (`memNo`) USING BTREE,
  KEY `memNo_11` (`memNo`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('a22753516@gmail.com','a877499a','劉安','台北市濟南路九段9號9樓','0912345678',1,0),('aaaa22753516@kimo.com','0987559018','劉小安',NULL,NULL,1,0),('chia@g.com','12345','chiayu','台北北','0933',0,0),('chia2@g.com','12345','chia',NULL,NULL,0,0),('testUser@test.com.tw','12345','test',NULL,NULL,0,0);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_detail` (
  `oNo` int(11) NOT NULL AUTO_INCREMENT,
  `orNo` int(11) NOT NULL,
  `gNo` int(11) NOT NULL,
  `gAmount` int(11) NOT NULL,
  PRIMARY KEY (`oNo`),
  KEY `orNo_idx` (`orNo`),
  KEY `gNo5_idx` (`gNo`),
  CONSTRAINT `gNo5` FOREIGN KEY (`gNo`) REFERENCES `goods` (`gNo`),
  CONSTRAINT `orNo` FOREIGN KEY (`orNo`) REFERENCES `order_record` (`orNo`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (30,39,2,4),(31,41,4,4),(32,42,3,2),(33,43,47,2),(34,43,46,1),(35,44,37,2),(36,44,34,2),(37,45,38,2),(38,45,34,3),(39,46,20,1);
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_record`
--

DROP TABLE IF EXISTS `order_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_record` (
  `orNo` int(11) NOT NULL AUTO_INCREMENT,
  `memNo` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `orTime` datetime NOT NULL,
  `state` int(11) NOT NULL DEFAULT '0',
  `orAddress` varchar(128) COLLATE utf8_unicode_ci NOT NULL,
  `orPayment` varchar(45) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'cash',
  `orTel` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `orTotalPrice` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`orNo`) USING BTREE,
  KEY `memNo3` (`memNo`) USING BTREE,
  CONSTRAINT `memNo3` FOREIGN KEY (`memNo`) REFERENCES `member` (`memNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_record`
--

LOCK TABLES `order_record` WRITE;
/*!40000 ALTER TABLE `order_record` DISABLE KEYS */;
INSERT INTO `order_record` VALUES (39,'a22753516@gmail.com','2021-11-20 16:02:29',4,'台北市中山區濟南路一段321號','信用卡/金融卡','0932222223',4060),(41,'a22753516@gmail.com','2021-11-20 16:16:49',1,'台北市中山區濟南路一段321號','信用卡/金融卡','0932222223',20060),(42,'a22753516@gmail.com','2021-11-20 18:15:37',0,'台北市中山區濟南路一段321號','信用卡/金融卡','0932222223',460),(43,'a22753516@gmail.com','2021-11-20 19:40:53',0,'台北市中山區濟南路一段321號','信用卡/金融卡','0932222223',10060),(44,'a22753516@gmail.com','2021-11-21 15:44:53',1,'台北市中正區濟南路一段321號','信用卡/金融卡','0911111111',1660),(45,'a22753516@gmail.com','2021-11-22 09:55:16',0,'台北市中正區濟南路一段321號','信用卡/金融卡','0911111111',2160),(46,'testUser@test.com.tw','2021-11-22 15:58:02',0,'台北市中正區濟南路一段321號','信用卡/金融卡','0911222325',500060);
/*!40000 ALTER TABLE `order_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopping_cart`
--

DROP TABLE IF EXISTS `shopping_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shopping_cart` (
  `scNo` int(11) NOT NULL AUTO_INCREMENT,
  `memNo` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `gNo` int(11) NOT NULL,
  `gAmount` int(255) NOT NULL,
  PRIMARY KEY (`scNo`) USING BTREE,
  KEY `memNo2` (`memNo`) USING BTREE,
  KEY `gNo` (`gNo`) USING BTREE,
  CONSTRAINT `gNo` FOREIGN KEY (`gNo`) REFERENCES `goods` (`gNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `memNo2` FOREIGN KEY (`memNo`) REFERENCES `member` (`memNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopping_cart`
--

LOCK TABLES `shopping_cart` WRITE;
/*!40000 ALTER TABLE `shopping_cart` DISABLE KEYS */;
INSERT INTO `shopping_cart` VALUES (2,'aaaa22753516@kimo.com',5,1),(4,'a22753516@gmail.com',8,5),(48,'chia@g.com',19,1),(56,'a22753516@gmail.com',3,1),(57,'a22753516@gmail.com',35,1),(58,'testUser@test.com.tw',3,1),(59,'testUser@test.com.tw',24,1),(60,'testUser@test.com.tw',32,2),(62,'a22753516@gmail.com',23,1);
/*!40000 ALTER TABLE `shopping_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'Gcp110203'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `clearEvents` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`andy`@`%`*/ /*!50106 EVENT `clearEvents` ON SCHEDULE EVERY 1 DAY STARTS '2021-11-27 15:51:00' ON COMPLETION NOT PRESERVE ENABLE DO delete from exhibition where eCheck=999 */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `endEvents` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`%`*/ /*!50106 EVENT `endEvents` ON SCHEDULE EVERY 1 DAY STARTS '2021-11-27 15:57:00' ON COMPLETION NOT PRESERVE ENABLE DO update exhibition set eCheck=7 where endTime < CURDATE() and eCheck=6 */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
/*!50106 DROP EVENT IF EXISTS `startEvents` */;;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`andy`@`%`*/ /*!50106 EVENT `startEvents` ON SCHEDULE EVERY 1 DAY STARTS '2021-11-27 16:05:00' ON COMPLETION NOT PRESERVE ENABLE DO update Gcp110203.exhibition  set eCheck=6 where startTime <= CURDATE() AND endTime >=CURDATE() and eCheck=5 */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- GTID state at the end of the backup 
--

SET @@GLOBAL.GTID_PURGED='91efb285-332c-11ec-8265-42010a8c0002:1-176916';
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-28 10:13:47
