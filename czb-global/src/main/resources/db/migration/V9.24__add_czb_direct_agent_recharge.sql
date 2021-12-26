DROP TABLE IF EXISTS `czb_direct_agent_recharge`;
CREATE TABLE `czb_direct_agent_recharge`  (
  `direct_recharge_id` varchar(255) NOT NULL COMMENT '订单信息',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `recharge_amount` double(10,2) COMMENT '充值金额',
  `amount_recovered` double(10,2) COMMENT '圈回金额',
  `balance` double(10,2) COMMENT '余额',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`direct_recharge_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
