# 데이터 베이스 생성
DROP DATABASE IF EXISTS `pptor`;
CREATE DATABASE `pptor`;
USE `pptor`;

# 계정 생성
GRANT ALL PRIVILEGES
ON *.*
TO `team`@`%`
IDENTIFIED BY 'two';

# 테이블 생성 시작
## 멤버 테이블
CREATE TABLE `member` (
   `member_id` INTEGER UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `auth_level` INTEGER DEFAULT '3',
    `blind` TINYINT DEFAULT 0,
    `email` VARCHAR(255) NOT NULL,
    `login_id` VARCHAR(255) NOT NULL,
    `login_pw` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `nickname` VARCHAR(255) NOT NULL,
    `reg_date` DATETIME NOT NULL,
    `update_date` DATETIME NOT NULL
);

## 게시판 테이블
CREATE TABLE `board` (
       `board_id` INTEGER UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
        `name` VARCHAR(255)
);

## 게시물 테이블
CREATE TABLE `article` (
   `article_id` INTEGER UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `blind` TINYINT DEFAULT 0,
    `body` VARCHAR(255) NOT NULL,
    `reg_date` DATETIME NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `update_date` DATETIME NOT NULL,
    board_id INTEGER,
    member_id INTEGER
);

## 마이페이지 테이블
CREATE TABLE `my_page` (
   `my_page_id` INTEGER UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    artc_dir VARCHAR(255) NOT NULL,
    `member_id` INTEGER
);


#alter table article
#   add constraint FK2y7w132xb5xp1aiouig87aqjo
#   foreign key (board_id)
#  references board (board_id)

#alter table article
#  add constraint FK6l9vkfd5ixw8o8kph5rj1k7gu
#   foreign key (member_id)
#   references member (member_id)

#alter table my_page
#   add constraint FKmsx4slak6ylxwjvs2pyhm6ich
#   foreign key (member_id)
#   references member (member_id)
