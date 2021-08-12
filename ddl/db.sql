DROP DATABASE IF EXISTS `pptor`;
CREATE DATABASE `pptor`;
USE `pptor`;

GRANT ALL PRIVILEGES
ON *.*
TO `team`@`%`
IDENTIFIED BY 'two';

#CREATE TABLE `member` (
#    `id` INT(100) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
#    `loginId` VARCHAR(100) NOT NULL,
#    `loginPw` VARCHAR(100) NOT NULL,
#    `name` VARCHAR(100) NOT NULL,
#    `nickname` VARCHAR(100) NOT NULL,
#    `email` VARCHAR(100) NOT NULL,
#    `regDate` DATETIME NOT NULL,
#    `updateDate` DATETIME NOT NULL,
#    `blind` TINYINT(2) NOT NULL DEFAULT 0,
#    `authLevel` SMALLINT(2) UNSIGNED NOT NULL DEFAULT 3
#);
