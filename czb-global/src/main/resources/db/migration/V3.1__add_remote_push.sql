-- ----------------------------
-- Table structure for czb_remote_push
-- ----------------------------
DROP TABLE IF EXISTS `czb_remote_push`;
CREATE TABLE `czb_remote_push`  (
  `device_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_token` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_type` int(1) NULL DEFAULT 0,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`device_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for czb_remote_push_notice
-- ----------------------------
DROP TABLE IF EXISTS `czb_remote_push_notice`;
CREATE TABLE `czb_remote_push_notice`  (
  `notice_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `device_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notice_content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notice_type` int(1) NULL DEFAULT 0,
  `app_router_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
   `deadline` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;




