
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for czb_weChat_commodity_order
-- ----------------------------
DROP TABLE IF EXISTS `czb_wechat_commodity_order`;
CREATE TABLE `czb_wechat_commodity_order`  (
  `order_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `actual_price` double(10, 2) NULL DEFAULT NULL,
  `third_order` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_status` int(1) NULL DEFAULT NULL,
  `pay_method` int(1) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `electronic_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_state` int(1) NULL DEFAULT NULL,
  `address_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `qrcode` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fy_money` double(10, 2) NULL DEFAULT NULL,
  `cost_price` double(10, 2) NULL DEFAULT NULL,
  `commodity_type` int(1) NULL DEFAULT NULL,
  `commmodity_type_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `processing_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `handler` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_num` int(3) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_category
-- ----------------------------
DROP TABLE IF EXISTS `czb_category`;
CREATE TABLE `czb_category`  (
  `category_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `superior_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_weChat_commodity
-- ----------------------------
DROP TABLE IF EXISTS `czb_wechat_commodity`;
CREATE TABLE `czb_wechat_commodity`  (
  `commodity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shop_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `area` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_info` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `discount` double(10,2) NULL DEFAULT NULL,
  `commodity_img_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cost_price` double(10,2) NULL DEFAULT NULL,
  `original_price` double(10,2) NULL DEFAULT NULL,
  `sale_price` double(10,2) NULL DEFAULT NULL,
  `commmodity_type_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `starting_time_business` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `end_time_business` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `commodity_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `commodity_num` int NULL DEFAULT NULL,
  `is_sale` int(1) NULL DEFAULT NULL,
  `take_way` int(1) NULL DEFAULT NULL,
  `shop_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_content` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_address` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `longitude` double(10,5) NULL DEFAULT NULL,
  `latitude` double(10,5) NULL DEFAULT NULL,
  `is_have_shop` int(1) NULL DEFAULT NULL,
  `fy_money` double(10, 2) NULL DEFAULT NULL,
  `show_place` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `show_order` int(4) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`commodity_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_wc_goods_delivery_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_wechat_goods_delivery_records`;
CREATE TABLE `czb_wechat_goods_delivery_records` (
  `record_id` varchar(20) NOT NULL,
  `order_id` varchar(20) DEFAULT NULL,
  `address_id` varchar(20) DEFAULT NULL,
  `delivery_state` int(1) DEFAULT NULL COMMENT '0 未邮寄，1 邮寄中，2 已收货',
  `delivery_num` varchar(50) DEFAULT NULL,
  `delivery_company` varchar(50) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for czb_wc_vip_apply
-- ----------------------------
DROP TABLE IF EXISTS `czb_wechat_vip_apply`;
CREATE TABLE `czb_wechat_vip_apply` (
  `record_id` varchar(20) NOT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `phone_num` varchar(20) DEFAULT NULL,
  `user_name` varchar(10) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` int default 0,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
