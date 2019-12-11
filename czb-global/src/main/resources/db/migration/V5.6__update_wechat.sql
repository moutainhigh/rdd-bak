alter table czb_user add bindingId varchar(20) character  set utf8 collate utf8_general_ci null default null COMMENT '微信用户绑定的人多多用户账号电话id';
alter table czb_wechat_commodity add norms_id varchar(20) character  set utf8 collate utf8_general_ci null default null COMMENT '分类id';

-- ----------------------------
-- Table structure for czb_wechat_norms
-- ----------------------------
DROP TABLE IF EXISTS `czb_wechat_norms`;
CREATE TABLE `czb_wechat_norms`  (
  `norms_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `origin_price` double(10, 2) NULL DEFAULT NULL,
  `norms_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `norms_content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sale_price` double(10, 2) NULL DEFAULT 0.0,
  `file_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_num` int(4) NULL DEFAULT 0,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`norms_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;