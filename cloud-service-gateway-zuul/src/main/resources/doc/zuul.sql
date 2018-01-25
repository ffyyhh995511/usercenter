/*
Navicat MySQL Data Transfer

Source Server         : 本地开发
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : zuul

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-25 18:21:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gateway_api_define
-- ----------------------------
DROP TABLE IF EXISTS `gateway_api_define`;
CREATE TABLE `gateway_api_define` (
  `id` varchar(50) NOT NULL,
  `path` varchar(255) NOT NULL,
  `service_id` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `retryable` tinyint(1) DEFAULT NULL,
  `enabled` tinyint(1) NOT NULL,
  `strip_prefix` int(11) DEFAULT NULL,
  `api_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gateway_api_define
-- ----------------------------
INSERT INTO `gateway_api_define` VALUES ('1', '/usercenter/**', 'cloud-service-user', null, '0', '1', '1', null);
INSERT INTO `gateway_api_define` VALUES ('2', '/testA/', 'test-A', null, null, '1', '2', null);
INSERT INTO `gateway_api_define` VALUES ('pppp', '/pppp/**', null, 'http://localhost:8090', '0', '1', '1', null);
INSERT INTO `gateway_api_define` VALUES ('xxxx', '/xxxx/**', null, 'http://localhost:8090', '0', '1', '1', null);
