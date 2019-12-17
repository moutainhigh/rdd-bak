-- ----------------------------
-- Table structure for czb_partner_vip_income
-- ----------------------------
DROP TABLE IF EXISTS `czb_partner_vip_income`;
CREATE TABLE `czb_partner_vip_income`  (
  `partner_vip_income_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '合伙人vip返佣收益	id',
  `partner_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '合伙人id',
  `partner_type`int(1) NULL DEFAULT NULL,
  `is_settle`int(1) NULL DEFAULT 0,
  `vip_add_count`int(10) NULL DEFAULT 0,
  `start_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `end_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `vip_add_income`double(10,2) DEFAULT 0,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`partner_vip_income_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;