# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.18)
# Database: vat_tool
# Generation Time: 2017-10-25 20:13:04 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table shape
# ------------------------------------------------------------

DROP TABLE IF EXISTS `shape`;

CREATE TABLE `shape` (
  `shape_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(11) NOT NULL DEFAULT '',
  PRIMARY KEY (`shape_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `shape` WRITE;
/*!40000 ALTER TABLE `shape` DISABLE KEYS */;

INSERT INTO `shape` (`shape_id`, `name`)
VALUES
	(1,'cilinder'),
	(5,'sphere'),
	(6,'cubic');

/*!40000 ALTER TABLE `shape` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table shape_objects
# ------------------------------------------------------------

DROP TABLE IF EXISTS `shape_objects`;

CREATE TABLE `shape_objects` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `shape` int(11) unsigned DEFAULT '0',
  `height` double DEFAULT NULL,
  `radius` double DEFAULT NULL,
  `width` double DEFAULT NULL,
  `length` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `shape` (`shape`),
  CONSTRAINT `shape_objects_ibfk_1` FOREIGN KEY (`shape`) REFERENCES `shape` (`shape_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `shape_objects` WRITE;
/*!40000 ALTER TABLE `shape_objects` DISABLE KEYS */;

INSERT INTO `shape_objects` (`id`, `shape`, `height`, `radius`, `width`, `length`)
VALUES
	(7,5,20.5,20.5,20.5,20.5),
	(13,6,40.5,40.5,40.5,40.5),
	(15,1,10.4,10.4,10.4,10.4),
	(17,1,432,432,10.4,10.4),
	(18,1,10,10,10.4,10.4),
	(19,1,543,543,10.4,10.4),
	(20,6,40.5,40.5,40.5,40.5),
	(21,1,322,432,10.4,10.4),
	(22,1,10.4,10.4,10.4,10.4),
	(24,5,20.5,20.5,20.5,20.5),
	(25,6,40.5,40.5,40.5,40.5),
	(26,1,432,123,10.4,10.4),
	(111,1,234.2,23.2,NULL,NULL),
	(112,1,422.2,432.2,NULL,NULL),
	(113,1,5543.3,432.4,NULL,NULL),
	(114,1,534.3,654.4,NULL,NULL),
	(115,6,42.4,NULL,44.2,22.2),
	(116,5,NULL,432,NULL,NULL),
	(117,1,543,543,NULL,NULL),
	(119,1,10,10,NULL,NULL),
	(120,6,30,NULL,40,20),
	(121,5,NULL,20,NULL,NULL),
	(122,5,NULL,2432,NULL,NULL),
	(123,6,30.5,NULL,20.5,20.5),
	(124,6,30.5,NULL,20.5,20.5),
	(125,6,30.5,NULL,20.5,20.5),
	(126,5,NULL,20.5,NULL,NULL),
	(127,1,30.5,20.5,NULL,NULL),
	(128,6,30.5,NULL,20.5,20.5),
	(129,5,NULL,20.5,NULL,NULL),
	(130,1,30.5,20.5,NULL,NULL),
	(131,6,30.5,NULL,20.5,20.5),
	(132,5,NULL,20.5,NULL,NULL),
	(133,1,30.5,20.5,NULL,NULL),
	(134,6,30.5,NULL,20.5,20.5),
	(135,5,NULL,20.5,NULL,NULL),
	(136,1,30.5,20.5,NULL,NULL),
	(137,1,234.2,432.2,NULL,NULL),
	(138,1,234.2,432.2,NULL,NULL);

/*!40000 ALTER TABLE `shape_objects` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
