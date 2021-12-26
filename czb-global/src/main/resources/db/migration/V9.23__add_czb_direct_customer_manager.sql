DROP TABLE IF EXISTS `czb_direct_customer_manager`;
CREATE TABLE `czb_direct_customer_manager`  (
  `user_id` varchar(255) NOT NULL COMMENT 'id',
  `recharge_amount` double(10,2) COMMENT '充值金额',
  `balance` double(10,2) COMMENT '余额',
  `consumption_amount` double(10,2) COMMENT '消费金额',
  `create_at` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_at` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `amount_recovered` double(10,2) COMMENT '圈回余额',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
