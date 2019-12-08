alter table czb_wechat_commodity_order add is_have_settled INTEGER (1) default 0 COMMENT '是否已经结算';
alter table czb_wechat_commodity add commodity_introduce varchar(10000) character  set utf8 collate utf8_general_ci null default null COMMENT '商品介绍公共';
alter table czb_wechat_commodity_order add settled_record_id varchar(20) character  set utf8 collate utf8_general_ci null default null COMMENT '结算id';

-- ----------------------------
-- Table structure for czb_wechat_settle_record
-- ----------------------------
DROP TABLE IF EXISTS `czb_wechat_settle_record`;
CREATE TABLE `czb_wechat_settle_record`  (
  `record_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `settle_user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `settled_user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shop_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `total_amount` double(10, 2) NULL DEFAULT NULL,
  `settle_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;