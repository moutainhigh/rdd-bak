/*
Navicat MySQL Data Transfer

Source Server         : 39.98.250.45_3306
Source Server Version : 50725
Source Host           : 39.98.250.45:3306
Source Database       : czb_temp

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-09-18 12:06:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for czb_dish
-- ----------------------------
DROP TABLE IF EXISTS `czb_dish`;
CREATE TABLE `czb_dish`  (
  `dish_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `commodity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dish_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dish_type` int(1) NULL DEFAULT NULL,
  `supply_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_sale` int(1) NULL DEFAULT NULL,
  `label` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `original_price` double(10, 2) NULL DEFAULT NULL,
  `current_price` double(10, 2) NULL DEFAULT NULL,
  `vip_price` double(10, 2) NULL DEFAULT NULL,
  `file_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`dish_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for czb_dish_relationship
-- ----------------------------
DROP TABLE IF EXISTS `czb_dish_relationship`;
CREATE TABLE `czb_dish_relationship`  (
  `dish_relationship_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `set_meal_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `single_product_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
   `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`dish_relationship_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for czb_dish_order
-- ----------------------------
DROP TABLE IF EXISTS `czb_dish_order`;
CREATE TABLE `czb_dish_order`  (
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `actual_price` double(10, 2) NULL DEFAULT NULL,
  `third_order` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_status` int(1) NULL DEFAULT NULL,
  `pey_method` int(1) NULL DEFAULT NULL,
  `dining_status` int(1) NULL DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for czb_order_dishes
-- ----------------------------
DROP TABLE IF EXISTS `czb_order_dishes`;
CREATE TABLE `czb_order_dishes`  (
  `order_dishes_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dish_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dish_type` int(1) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`order_dishes_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



alter table czb_commodity add commmodity_type INT( 1 ) DEFAULT 0 COMMENT '商店类型';
alter table czb_commodity add longitude double(10,2)  DEFAULT 0.0 COMMENT '经纬度';
alter table czb_commodity add latitude double(10,2)  DEFAULT 0.0 COMMENT '经纬度';
ALTER TABLE czb_commodity ADD starting_time_business TIMESTAMP NULL COMMENT '营业开始时间';
ALTER TABLE czb_commodity ADD end_time_business TIMESTAMP NULL COMMENT '营业结束时间';
