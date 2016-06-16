/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.13-log : Database - nutzbook
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`nutzbook` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `nutzbook`;

/*Table structure for table `pro_address` */

DROP TABLE IF EXISTS `pro_address`;

CREATE TABLE `pro_address` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `user_id` int(32) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `is_delete` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_address` */

/*Table structure for table `pro_company` */

DROP TABLE IF EXISTS `pro_company`;

CREATE TABLE `pro_company` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `pro_company` */

insert  into `pro_company`(`id`,`name`,`create_time`) values (1,'ppmoney','2016-06-15 16:23:13'),(2,'万惠','2016-06-15 16:23:28');

/*Table structure for table `pro_relation` */

DROP TABLE IF EXISTS `pro_relation`;

CREATE TABLE `pro_relation` (
  `id` int(32) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `relation` varchar(50) DEFAULT NULL,
  `isDelete` int(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pro_relation` */

/*Table structure for table `pro_scope` */

DROP TABLE IF EXISTS `pro_scope`;

CREATE TABLE `pro_scope` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `pid` int(32) DEFAULT NULL,
  `is_delete` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `pro_scope` */

insert  into `pro_scope`(`id`,`name`,`pid`,`is_delete`) values (1,'华南',0,0),(2,'广东省',1,0),(3,'广州市',2,0),(4,'福建省',1,0),(5,'福建省的某市',4,0);

/*Table structure for table `pro_user` */

DROP TABLE IF EXISTS `pro_user`;

CREATE TABLE `pro_user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `compony` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `is_enable` int(32) DEFAULT NULL,
  `enable_desc` varchar(50) DEFAULT NULL,
  `is_delete` int(32) DEFAULT NULL,
  `province_id` int(32) DEFAULT NULL,
  `city_id` int(32) DEFAULT NULL,
  `region_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `pro_user` */

insert  into `pro_user`(`id`,`name`,`compony`,`address`,`phone`,`email`,`birth`,`create_time`,`is_enable`,`enable_desc`,`is_delete`,`province_id`,`city_id`,`region_id`) values (1,'zz','ppmoney','天河区','524242','4242424','2016-06-16','2016-06-16',0,'无效',1,2,3,1),(2,'zzzzzzz','ppmoney',NULL,'15616161651','15555@163.com','2016-06-22','2016-06-16',1,'有效',0,2,3,1);

/*Table structure for table `t_big_content` */

DROP TABLE IF EXISTS `t_big_content`;

CREATE TABLE `t_big_content` (
  `id` varchar(50) NOT NULL,
  `dt` mediumblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_big_content` */

/*Table structure for table `t_logs` */

DROP TABLE IF EXISTS `t_logs`;

CREATE TABLE `t_logs` (
  `log_level` varchar(10) NOT NULL,
  `class_name` varchar(2028) NOT NULL,
  `method_name` varchar(1024) NOT NULL,
  `line_number` varchar(50) NOT NULL,
  `mid` varchar(2028) NOT NULL,
  `content` varchar(10000) NOT NULL,
  `insert_time` varchar(64) NOT NULL
) ENGINE=CSV DEFAULT CHARSET=utf8;

/*Data for the table `t_logs` */

/*Table structure for table `t_oauth_user` */

DROP TABLE IF EXISTS `t_oauth_user`;

CREATE TABLE `t_oauth_user` (
  `pvd` varchar(50) NOT NULL,
  `vid` varchar(50) NOT NULL,
  `u_id` int(32) DEFAULT NULL,
  `a_url` varchar(8192) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`pvd`,`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_oauth_user` */

/*Table structure for table `t_openvpn_client` */

DROP TABLE IF EXISTS `t_openvpn_client`;

CREATE TABLE `t_openvpn_client` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) NOT NULL,
  `macid` varchar(50) DEFAULT NULL,
  `pf` varchar(50) DEFAULT NULL,
  `_key` varchar(50) DEFAULT NULL,
  `f` varchar(50) DEFAULT NULL,
  `stat` int(32) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ip` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_openvpn_client` */

/*Table structure for table `t_permission` */

DROP TABLE IF EXISTS `t_permission`;

CREATE TABLE `t_permission` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `al` varchar(50) DEFAULT NULL,
  `dt` varchar(500) DEFAULT NULL,
  `permission_category_id` varchar(50) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*Data for the table `t_permission` */

insert  into `t_permission`(`id`,`name`,`al`,`dt`,`permission_category_id`,`ct`,`ut`) values (1,'user:view',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(2,'authority:permission:query',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(3,'topic:index:rebuild',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(4,'authority:user:query',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(5,'permission:add',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(6,'user:query',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(7,'role:add',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(8,'permission:delete',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(9,'authority:permission:delete',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(10,'role:view',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(11,'authority:role:delete',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(12,'authority:role:add',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(13,'authority:permission:update',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(14,'permission:edit',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(15,'sysconf:update',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(16,'permission:view',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(17,'user:add',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(18,'user:delete',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(19,'authority:role:query',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(20,'topic:delete',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(21,'user:update',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(22,'authority:role:update',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(23,'topic:view',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(24,'topic:update',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(25,'topic:expstatic',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(26,'authority:permission:add',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(27,'user:edit',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(28,'system.permission:add',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(29,'role:edit',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(30,'sysconf:reload',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(31,'topic:update:tags',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(32,'authority:user:update',NULL,NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22');

/*Table structure for table `t_permission_category` */

DROP TABLE IF EXISTS `t_permission_category`;

CREATE TABLE `t_permission_category` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `is_locked` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_permission_category` */

/*Table structure for table `t_person` */

DROP TABLE IF EXISTS `t_person`;

CREATE TABLE `t_person` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `age` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_person` */

insert  into `t_person`(`id`,`name`,`age`) values (1,'zjcscut',23),(2,'pp',99),(3,'zjc2',23),(4,'zjc3',24);

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `al` varchar(50) DEFAULT NULL,
  `dt` varchar(500) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`name`,`al`,`dt`,`ct`,`ut`) values (1,'admin',NULL,NULL,'2016-06-12 11:51:22','2016-06-12 11:51:22');

/*Table structure for table `t_role_permission` */

DROP TABLE IF EXISTS `t_role_permission`;

CREATE TABLE `t_role_permission` (
  `role_id` bigint(64) DEFAULT NULL,
  `permission_id` bigint(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role_permission` */

insert  into `t_role_permission`(`role_id`,`permission_id`) values (1,1),(1,2),(1,3),(1,4),(1,6),(1,9),(1,11),(1,12),(1,13),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,31),(1,32);

/*Table structure for table `t_sys_configure` */

DROP TABLE IF EXISTS `t_sys_configure`;

CREATE TABLE `t_sys_configure` (
  `k` varchar(50) NOT NULL,
  `v` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`k`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_configure` */

/*Table structure for table `t_syslog_` */

DROP TABLE IF EXISTS `t_syslog_`;

CREATE TABLE `t_syslog_` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `t` varchar(50) DEFAULT NULL,
  `tg` varchar(50) DEFAULT NULL,
  `src` varchar(1024) DEFAULT NULL,
  `u_id` int(32) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `msg` varchar(8192) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_syslog_` */

/*Table structure for table `t_syslog_201606` */

DROP TABLE IF EXISTS `t_syslog_201606`;

CREATE TABLE `t_syslog_201606` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `t` varchar(50) DEFAULT NULL,
  `tg` varchar(50) DEFAULT NULL,
  `src` varchar(1024) DEFAULT NULL,
  `u_id` int(32) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `msg` varchar(8192) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_syslog_201606` */

insert  into `t_syslog_201606`(`id`,`t`,`tg`,`src`,`u_id`,`ip`,`msg`,`ct`) values (1,'aop.after','用户管理,新增用户','net.wendal.nutzbook.service.UserService#add',-1,'','用户名[admin]','2016-06-12 11:51:22'),(2,'aop.after','用户管理,新增用户','net.wendal.nutzbook.service.UserService#add',-1,'','用户名[guest]','2016-06-12 11:51:22');

/*Table structure for table `t_syslog_201607` */

DROP TABLE IF EXISTS `t_syslog_201607`;

CREATE TABLE `t_syslog_201607` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `t` varchar(50) DEFAULT NULL,
  `tg` varchar(50) DEFAULT NULL,
  `src` varchar(1024) DEFAULT NULL,
  `u_id` int(32) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `msg` varchar(8192) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_syslog_201607` */

/*Table structure for table `t_topic` */

DROP TABLE IF EXISTS `t_topic`;

CREATE TABLE `t_topic` (
  `id` varchar(50) NOT NULL,
  `title` varchar(4096) DEFAULT NULL,
  `tp` varchar(20) DEFAULT NULL,
  `cnt` varchar(50) DEFAULT NULL,
  `cid` varchar(50) DEFAULT NULL,
  `tags` varchar(128) DEFAULT NULL,
  `u_id` int(32) DEFAULT NULL,
  `c_top` tinyint(1) DEFAULT NULL,
  `c_good` tinyint(1) DEFAULT NULL,
  `c_lock` tinyint(1) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_topic` */

/*Table structure for table `t_topic_reply` */

DROP TABLE IF EXISTS `t_topic_reply`;

CREATE TABLE `t_topic_reply` (
  `id` varchar(50) NOT NULL,
  `topicId` varchar(50) DEFAULT NULL,
  `replyTo` varchar(50) DEFAULT NULL,
  `u_id` int(32) DEFAULT NULL,
  `cnt` varchar(50) DEFAULT NULL,
  `cid` varchar(50) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_topic_reply` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `passwd` varchar(128) DEFAULT NULL,
  `salt` varchar(50) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`name`,`passwd`,`salt`,`locked`,`ct`,`ut`) values (1,'admin','7b0bbb9c6fe803ce8df5b63b6f8b8846ebb87d297fce32e87eb5e64ddd0e615e','f7d774d9510745b4a5d56a368278be45',0,'2016-06-12 11:51:22','2016-06-12 11:51:22'),(2,'guest','cff9a7ab5677a1c31190683dad0922fa7e59829f10c35e6be8645939346ed81d','d192f4a394df47168ac1acaec37bc900',0,'2016-06-12 11:51:22','2016-06-12 11:51:22');

/*Table structure for table `t_user_message` */

DROP TABLE IF EXISTS `t_user_message`;

CREATE TABLE `t_user_message` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `sender_id` int(32) DEFAULT NULL,
  `revc_id` int(32) DEFAULT NULL,
  `cnt` varchar(4096) DEFAULT NULL,
  `unread` tinyint(1) DEFAULT NULL,
  `tip` varchar(50) DEFAULT NULL,
  `rip` varchar(50) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `t_user_message` */

/*Table structure for table `t_user_permission` */

DROP TABLE IF EXISTS `t_user_permission`;

CREATE TABLE `t_user_permission` (
  `u_id` int(32) DEFAULT NULL,
  `permission_id` bigint(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_permission` */

/*Table structure for table `t_user_profile` */

DROP TABLE IF EXISTS `t_user_profile`;

CREATE TABLE `t_user_profile` (
  `u_id` int(32) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `email_checked` tinyint(1) DEFAULT NULL,
  `avatar` mediumblob,
  `gender` varchar(50) DEFAULT NULL,
  `dt` varchar(50) DEFAULT NULL,
  `loc` varchar(50) DEFAULT NULL,
  `loginname` varchar(50) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_profile` */

insert  into `t_user_profile`(`u_id`,`nickname`,`email`,`email_checked`,`avatar`,`gender`,`dt`,`loc`,`loginname`,`ct`,`ut`) values (1,'admin',NULL,0,NULL,NULL,NULL,NULL,'admin','2016-06-12 11:51:22','2016-06-12 11:51:22'),(2,'游客',NULL,0,NULL,NULL,NULL,NULL,'guest','2016-06-12 11:51:22','2016-06-12 11:51:22');

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `u_id` int(32) DEFAULT NULL,
  `role_id` bigint(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`u_id`,`role_id`) values (1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
