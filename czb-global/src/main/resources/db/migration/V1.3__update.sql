/*
Navicat MySQL Data Transfer

Source Server         : 39.98.250.45_3306
Source Server Version : 50725
Source Host           : 39.98.250.45:3306
Source Database       : czb

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-27 20:48:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for czb_enterprise_consulting_info
-- ----------------------------
DROP TABLE IF EXISTS `czb_enterprise_consulting_info`;
CREATE TABLE `czb_enterprise_consulting_info` (
  `consulting_id` varchar(20) NOT NULL,
  `enterprise_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `contact_phone` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_handled` int(1) DEFAULT NULL,
  `applicant_id` varchar(20) DEFAULT NULL,
  `applicant_account` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`consulting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;