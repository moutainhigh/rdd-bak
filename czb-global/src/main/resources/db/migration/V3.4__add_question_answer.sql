-- ----------------------------
-- Table structure for czb_question_answer
-- ----------------------------
DROP TABLE IF EXISTS `czb_question_answer`;
CREATE TABLE `czb_question_answer`  (
  `question_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `question_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `answer_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `question_type` int(1) NULL DEFAULT 0,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`question_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



