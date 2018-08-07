DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
);
DROP TABLE IF EXISTS `member_info`;
CREATE TABLE `member_info` (
  `id` bigint(20) NOT NULL,
  `company` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `end_year` char(4) DEFAULT NULL,
  `member_no` varchar(255) DEFAULT NULL,
  `name` char(20) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `telephone` char(20) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
);
INSERT INTO `hibernate_sequence` VALUES (362);