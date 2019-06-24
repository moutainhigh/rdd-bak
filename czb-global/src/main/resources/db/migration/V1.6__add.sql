alter table czb_income_log add target_year_month TIMESTAMP NULL COMMENT '补贴目标年月';

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for czb_shop
-- ----------------------------
DROP TABLE IF EXISTS `czb_shop`;
CREATE TABLE `czb_shop`  (
  `shop_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`shop_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_shop_commodity
-- ----------------------------
DROP TABLE IF EXISTS `czb_shop_commodity`;
CREATE TABLE `czb_shop_commodity`  (
  `commodity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shop_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `discount` double(3, 2) NULL DEFAULT NULL,
  `commodity_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_price` double(10, 2) NULL DEFAULT NULL,
  `classification` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`commodity_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_user_info_collected
-- ----------------------------
DROP TABLE IF EXISTS `czb_user_info_collected`;
CREATE TABLE `czb_user_info_collected`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '已采集用户信息id',
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单id',
  `info_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品用户信息采集id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采集信息内容',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_subsidy_mission
-- ----------------------------
DROP TABLE IF EXISTS `czb_subsidy_mission`;
CREATE TABLE `czb_subsidy_mission`  (
  `mission_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `target_year_month` timestamp(0) NULL DEFAULT NULL,
  `subsidy_percent` double(3, 2) NULL DEFAULT NULL,
  `state` int(1) NULL DEFAULT NULL,
  `partner` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`mission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_commodity_user_info_collection
-- ----------------------------
DROP TABLE IF EXISTS `czb_commodity_user_info_collection`;
CREATE TABLE `czb_commodity_user_info_collection`  (
  `info_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shop_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for msg_record
-- ----------------------------
DROP TABLE IF EXISTS `czb_msg_record`;
CREATE TABLE `czb_msg_record`  (
  `msg_record_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `msg_model_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `msg_announcer_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `msg_receiver_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `msg_state` int(1) NULL DEFAULT NULL,
  `alert` int(1) NULL DEFAULT NULL COMMENT '是否弹窗',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`msg_record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_msg_model
-- ----------------------------
DROP TABLE IF EXISTS `czb_msg_model`;
CREATE TABLE `czb_msg_model`  (
  `msg_model_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息模板id',
  `announcer_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `msg_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `msg_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`msg_model_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_order
-- ----------------------------
DROP TABLE IF EXISTS `czb_order`;
CREATE TABLE `czb_order`  (
  `id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comsumer_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `actual_price` double(10, 2) NULL DEFAULT NULL,
  `third_order` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_method` int(1) NULL DEFAULT NULL,
  `state` int(1) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;