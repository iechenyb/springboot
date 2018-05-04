/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-05-04 13:46:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(30) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `valid` tinyint(1) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25019377879351300 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'cyb', 'cyb', '1');
INSERT INTO `t_user` VALUES ('2', 'user', 'ee11cbb19052e40b07aac0ca060c23ee', '1');
INSERT INTO `t_user` VALUES ('3', 'admin', '21232f297a57a5a743894a0e4a801fc3', '1');
INSERT INTO `t_user` VALUES ('5', 'iechenyb', 'nottellyou', '0');
INSERT INTO `t_user` VALUES ('6', 'sssss', '123456', null);
INSERT INTO `t_user` VALUES ('25019377879351296', 'cj', '28198b369067e88dab9fefe85484dbf4', '1');
INSERT INTO `t_user` VALUES ('25019377879351297', 'zhangsan', '7d793037a0760186574b0282f2f435e7', '1');
