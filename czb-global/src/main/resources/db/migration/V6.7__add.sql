DROP TABLE IF EXISTS `czb_wechat_stock`;
CREATE TABLE `czb_wechat_stock`  (
  `stock_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `commodity_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stock_attr_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state`int(1) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`stock_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for czb_vip_area_config
-- ----------------------------
DROP TABLE IF EXISTS `czb_wechat_stock_attr`;
CREATE TABLE `czb_wechat_stock_attr`  (
  `stock_attr_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stock_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `attr_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`stock_attr_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;