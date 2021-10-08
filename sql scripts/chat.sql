DROP TABLE IF EXISTS `chat`;


CREATE TABLE `chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email1` varchar(50) DEFAULT NULL,
  `email2` varchar(50) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  `timing` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
