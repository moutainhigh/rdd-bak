-- ----------------------------
-- Table structure for czb_vip_area_config
-- ----------------------------
DROP TABLE IF EXISTS `czb_vip_area_config`;
CREATE TABLE `czb_vip_area_config`  (
  `vip_area_config_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'vip地域配置	id',
  `area` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vip_state`int(1) NULL DEFAULT NULL,
  `vip_price`double(10,2) DEFAULT NULL,
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`vip_area_config_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

alter table czb_user add is_vip INT( 1 ) DEFAULT 0 COMMENT '0不是vip，1是vip';

alter table czb_app_router add is_vip_path INT( 1 ) DEFAULT 0 COMMENT '0都展示1，只有vip展示';

alter table czb_vip_recharge_records add area varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐人';

ALTER TABLE czb_vip_recharge_records ADD recharge_time TIMESTAMP NULL COMMENT '充值时间';





