DROP TABLE IF EXISTS `czb_electricity_recharge_manager`;
CREATE TABLE `czb_electricity_recharge_manager`  (
  `order_id` varchar(255) NOT NULL COMMENT '订单号',
  `user_id` varchar(255) NOT NULL COMMENT '用户号',
  `regional` varchar(255) NOT NULL COMMENT '区域',
  `recharge_amount` double(10,2) COMMENT '充值金额',
  `real_amount` double(10,2) COMMENT '支付金额',
  `recharge_account` varchar(255) COMMENT '充值人账号',
  `state` integer(2) NOT NULL COMMENT '状态',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `finish_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '订单完成时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
