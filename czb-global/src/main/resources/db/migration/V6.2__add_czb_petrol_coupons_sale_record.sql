-- ----------------------------
-- Table structure for czb_petrol_sales_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_petrol_coupons_sales_records`;
CREATE TABLE `czb_petrol_coupons_sales_records` (
  `record_id` varchar(20) NOT NULL,
  `petrol_id` varchar(20) DEFAULT NULL,
  `buyer_id` varchar(20) DEFAULT NULL,
  `user_account` varchar(20) DEFAULT NULL,
  `payment_method` int(1) DEFAULT null,
  `to_rdd_out_trade_no` varchar(100) DEFAULT NULL,
  `to_rdd_third_order_id` varchar(100) DEFAULT NULL,
  `to_rdd_turnover_amount` double(10,2) DEFAULT NULL,
  `to_rdd_transaction_time` timestamp NULL DEFAULT NULL,
  `to_rdd_state` int(1) DEFAULT null,
  `unit_price` double(10,2) DEFAULT NULL,
  `to_lu_pay_start_time` timestamp NULL DEFAULT NULL,
  `to_lu_pay_end_time` timestamp NULL DEFAULT NULL,
  `to_lu_pay_state` int(1) DEFAULT null,
  `to_rdd_out_id` varchar(100) DEFAULT NULL,
  `return_order_id` varchar(100) DEFAULT NULL,
  `trading_id` varchar(100) DEFAULT NULL,
  `order_id` varchar(100) DEFAULT NULL,
  `order_info` varchar(100) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;