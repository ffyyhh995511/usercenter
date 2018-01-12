/*
Navicat MySQL Data Transfer

Source Server         : 本地开发
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : usercenter

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-12 13:03:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for business_type
-- ----------------------------
DROP TABLE IF EXISTS `business_type`;
CREATE TABLE `business_type` (
  `id` varchar(32) NOT NULL,
  `business_name` varchar(256) DEFAULT NULL COMMENT '业务名称',
  `app_id` int(4) DEFAULT NULL,
  `app_secret` varchar(25) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='各个接入的业务线';

-- ----------------------------
-- Table structure for snowflake_id_config
-- ----------------------------
DROP TABLE IF EXISTS `snowflake_id_config`;
CREATE TABLE `snowflake_id_config` (
  `mac` varchar(32) NOT NULL,
  `datacenter_id` int(5) NOT NULL,
  `worker_id` int(5) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`mac`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每台服务器配置的datacenterId和workerId,通过mac地址区分';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` bigint(18) NOT NULL,
  `username` varchar(25) DEFAULT NULL,
  `password` varchar(35) DEFAULT NULL,
  `telphone` varchar(11) DEFAULT NULL,
  `email` varchar(15) DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL COMMENT '状态：\r\n0：退出\r\n1：登陆\r\n2：黑名单',
  `token` varchar(256) DEFAULT NULL,
  `refresh_token` varchar(256) DEFAULT NULL,
  `token_expire` bigint(13) DEFAULT NULL COMMENT 'token过期时间（默认是7200秒）',
  `resresh_token_expire` bigint(13) DEFAULT NULL COMMENT 'refresh_token过期时间（默认30天）',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登陆时间',
  `last_logout_time` datetime DEFAULT NULL COMMENT '最近一次登出时间',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
