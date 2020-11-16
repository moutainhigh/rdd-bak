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
-- Table structure for czb_petrol_without_card
-- ----------------------------
DROP TABLE IF EXISTS `czb_petrol_without_card`;
CREATE TABLE `czb_petrol_without_card` (
   `petrol_id` varchar(20) NOT NULL,
  `petrol_num` varchar(20) DEFAULT NULL,
  `petrol_psw` varchar(20) DEFAULT NULL,
  `petrol_kind` int(1) DEFAULT NULL,
  `petrol_denomination` double(10,0) DEFAULT NULL,
  `available_integral` double(10,0) DEFAULT NULL,
  `points_to_be_loaded` double(10,0) DEFAULT NULL,
  `petrol_balance` double(10,2) DEFAULT NULL,
  `reserve_fund` double(10,2) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `discount` double(10,0) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `owner_id` varchar(20) DEFAULT NULL,
  `petrol_type` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `commission` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`petrol_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for czb_petrol_sales_records_without_card
-- ----------------------------
DROP TABLE IF EXISTS `czb_petrol_sales_records_without_card`;
CREATE TABLE `czb_petrol_sales_records_without_card` (
    `record_id` varchar(20) NOT NULL,
  `petrol_num` varchar(20) DEFAULT NULL,
  `buyer_id` varchar(20) DEFAULT NULL,
  `commodity_id` varchar(20) DEFAULT NULL,
  `payment_method` int(1) DEFAULT NULL,
  `third_order_id` varchar(80) DEFAULT NULL,
  `turnover_amount` double(10,2) DEFAULT NULL,
  `transaction_area` varchar(80) DEFAULT NULL,
  `transaction_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `petrol_id` varchar(20) DEFAULT NULL,
  `state` int(255) DEFAULT NULL,
  `contract_id` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `record_type` int(1) DEFAULT NULL,
  `is_recharged` int(1) DEFAULT NULL,
  `petrol_kind` int(1) DEFAULT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for czb_commodity_without_card
-- ----------------------------
DROP TABLE IF EXISTS `czb_commodity_without_card`;
CREATE TABLE `czb_commodity_without_card` (
    `commodity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `commodity_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `discount` double(3, 2) NULL DEFAULT NULL,
  `vip_discount` double(3, 2) NULL DEFAULT NULL,
  `commodity_price` double(10, 2) NULL DEFAULT NULL,
  `commodity_denomination` double(10, 2) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`commodity_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

