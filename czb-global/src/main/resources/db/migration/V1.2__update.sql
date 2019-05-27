/*
Navicat MySQL Data Transfer

Source Server         : 数据库服务器
Source Server Version : 50726
Source Host           : 121.41.9.13:9525
Source Database       : czb

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-05-26 21:17:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for czb_income_log
-- ----------------------------
DROP TABLE IF EXISTS `czb_income_log`;
CREATE TABLE `czb_income_log` (
  `record_id` varchar(20) NOT NULL,
  `amount` double(10,2) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `info_id` varchar(20) DEFAULT NULL,
  `before_change_income` double(10,2) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `souse_id` varchar(64) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `withdraw_to` varchar(50) DEFAULT NULL,
  `withdraw_name` varchar(50) DEFAULT NULL,
  `withdraw_account` double(10,2) DEFAULT NULL,
  `commission_source_user` varchar(20) DEFAULT NULL,
  `commission_got_user` varchar(20) DEFAULT NULL,
  `commission_level` int(2) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_income_log
-- ----------------------------
