
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for czb_partner_change_record
-- ----------------------------
DROP TABLE IF EXISTS `czb_partner_change_record`;
CREATE TABLE `czb_partner_change_record`  (
  `record_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `before_change_level` int(1) NULL DEFAULT NULL,
  `after_change_level` int(1) NULL DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
