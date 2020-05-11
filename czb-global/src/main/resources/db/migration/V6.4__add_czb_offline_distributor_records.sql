-- ----------------------------
-- Table structure for czb_offline_distributor_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_offline_distributor_records`;
CREATE TABLE `czb_offline_distributor_records` (
  `recharge_id` varchar(20) NOT NULL,
  `buyer_id` varchar(20) DEFAULT NULL,
  `amount` double(10,2) DEFAULT NULL,
  `recharge_time` timestamp NULL DEFAULT NULL,
  `before_balance` double(10,2) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`recharge_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;