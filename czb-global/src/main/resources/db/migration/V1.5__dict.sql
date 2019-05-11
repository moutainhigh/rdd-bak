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

 Date: 11/05/2019 16:45:48
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for czb_dict
-- ----------------------------
DROP TABLE IF EXISTS `czb_dict`;
CREATE TABLE `czb_dict`  (
  `dict_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`dict_id`) USING BTREE
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
