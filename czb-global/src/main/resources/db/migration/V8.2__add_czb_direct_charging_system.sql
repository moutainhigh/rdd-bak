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
-- Table structure for czb_direct_charging_order
-- ----------------------------
DROP TABLE IF EXISTS `czb_direct_charging_order`;
CREATE TABLE `czb_direct_charging_order` (
   `order_id` varchar(20) NOT NULL,
   `third_order_id` varchar(80) DEFAULT NULL,
   `recharge_amount` double(10, 2) NULL DEFAULT NULL,
   `user_id` varchar(20) DEFAULT NULL,
    `payment_method` int(1) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `record_type` int(1) DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for czb_direct_charging_commodity
-- ----------------------------
DROP TABLE IF EXISTS `czb_direct_charging_commodity`;
CREATE TABLE `czb_direct_charging_commodity` (
    `commodity_id` varchar(20) NOT NULL,
    `amount` double(10, 2) NULL DEFAULT NULL,
   `discount` double(10,2) DEFAULT NULL,
   `preferential_price` double(10,2) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`commodity_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for czb_user_card_relation
-- ----------------------------
DROP TABLE IF EXISTS `czb_user_card_relation`;
CREATE TABLE `czb_user_card_relation` (
    `record_id` varchar(20) NOT NULL,
    `user_id` varchar(20) DEFAULT NULL,
   `petrol_id` varchar(20) DEFAULT NULL,
   `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


