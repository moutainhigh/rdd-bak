/*
 Navicat Premium Data Transfer

 Source Server         : czb_db
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 39.98.250.45:3306
 Source Schema         : czb

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 11/05/2019 22:04:10
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for czb_deposit_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_deposit_records`;
CREATE TABLE `czb_deposit_records`  (
  `record_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `payer` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `amount` double(10, 2) NULL DEFAULT NULL,
  `is_recharged` int(1) NULL DEFAULT NULL,
  `payer_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int(1) NULL DEFAULT NULL,
  `user_id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT NULL,
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`record_id`) USING BTREE
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
