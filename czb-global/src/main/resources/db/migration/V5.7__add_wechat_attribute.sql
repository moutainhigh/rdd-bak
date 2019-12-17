DROP TABLE IF EXISTS `czb_wechat_norms`;

alter table czb_wechat_commodity_order add attr_info varchar(255) character  set utf8 collate utf8_general_ci null default null COMMENT '商品属性信息';

-- ----------------------------
-- Table structure for czb_commodity_attr
-- ----------------------------
DROP TABLE IF EXISTS `czb_wechat_commodity_attr`;
CREATE TABLE `czb_wechat_commodity_attr`  (
  `commodity_attr_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `extra_fy_money` double(10, 2) NULL DEFAULT NULL,
  `commodity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attribute_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `extra_sale_money` double(10, 2) NULL DEFAULT 0.0,
  `file_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attr_order` int(4) NULL DEFAULT 0,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`commodity_attr_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_attribute
-- ----------------------------
DROP TABLE IF EXISTS `czb_attribute`;
CREATE TABLE `czb_attribute`  (
  `attribute_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`attribute_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;