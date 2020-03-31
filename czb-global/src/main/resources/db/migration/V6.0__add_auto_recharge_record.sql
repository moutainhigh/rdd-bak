SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for czb_auto_recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `czb_auto_recharge_record`;
CREATE TABLE `czb_auto_recharge_record` (
  `auto_recharge_record_id` varchar(20) NOT NULL,
  `petrol_num` varchar(20) DEFAULT NULL,
  `recharge_amount` double(10, 2) NULL DEFAULT NULL,
  `price` double(10, 2) NULL DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `recharge_time` timestamp NULL DEFAULT NULL,
  `order_time` timestamp NULL DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`auto_recharge_record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;