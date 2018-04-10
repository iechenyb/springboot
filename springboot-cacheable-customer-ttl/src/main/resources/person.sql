/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-06 21:10:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for person
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('wyf', '32', '合肥', '1');
INSERT INTO `person` VALUES ('xx', '31', '北京', '2');
INSERT INTO `person` VALUES ('yy', '30', '上海', '3');
INSERT INTO `person` VALUES ('zz', '29', '南京', '4');
INSERT INTO `person` VALUES ('aa', '28', '武汉', '5');
INSERT INTO `person` VALUES ('bb', '27', '合肥', '6');
INSERT INTO `person` VALUES ('测试', '50', '合肥', '7');
INSERT INTO `person` VALUES ('测试', '50', '合肥', '8');
