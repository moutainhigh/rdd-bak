/*
Navicat MySQL Data Transfer

Source Server         : 39.98.250.45_3306
Source Server Version : 50725
Source Host           : 39.98.250.45:3306
Source Database       : czb_temp

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-26 12:06:07
*/

-- ----------------------------
-- Table structure for czb_without_card_area_config
-- ----------------------------
DROP TABLE IF EXISTS `czb_without_card_area_config`;
CREATE TABLE `czb_without_card_area_config` (
    `petrol_config_id` varchar(20) NOT NULL,
  `petrol_type` int(1) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `sale_state` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`petrol_config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_without_card_area_config
-- ----------------------------

-- ----------------------------
-- Table structure for czb_announcement
-- ----------------------------
DROP TABLE IF EXISTS `czb_without_card_area_config`;
CREATE TABLE `czb_without_card_area_config` (
    `petrol_config_id` varchar(20) NOT NULL,
  `petrol_type` int(1) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `sale_state` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`petrol_config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

