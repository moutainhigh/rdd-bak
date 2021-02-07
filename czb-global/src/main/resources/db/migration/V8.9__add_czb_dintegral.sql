-- ----------------------------
-- Table structure for czb_integral_info
-- ----------------------------
DROP TABLE IF EXISTS `czb_integral_info`;
CREATE TABLE `czb_integral_info` (
    `integral_info_id` varchar(20) NOT NULL,
    `user_id` varchar(20) DEFAULT NULL,
     `got_total` int(8) DEFAULT NULL,
     `current_total` int(8) NULL DEFAULT NULL,
     `create_at` timestamp NULL DEFAULT NULL,
     `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`integral_info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for czb_integral_log
-- ----------------------------
DROP TABLE IF EXISTS `czb_integral_log`;
CREATE TABLE `czb_integral_log` (
    `integral_log_id` varchar(20) NOT NULL,
    `integral_info_id` varchar(20) NOT NULL,
    `user_id` varchar(20) DEFAULT NULL,
   `integral_log_type` int(1) DEFAULT NULL,
   `integral_amount` int(8) DEFAULT NULL,
  `order_id` varchar(20) NOT NULL,
  `before_integral_amount` int(8) NOT NULL,
  `remark` varchar(50) NOT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`integral_log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for czb_integral_deduction_info
-- ----------------------------
DROP TABLE IF EXISTS `czb_integral_deduction_info`;
CREATE TABLE `czb_integral_deduction_info` (
    `integral_deduction_info_id` varchar(20) NOT NULL,
    `deduction_type` int(1) NOT NULL,
    `max_deduction_amount` double(10, 2) NOT NULL,
    `commodity_id` varchar(20) NOT NULL,
       `create_at` timestamp NULL DEFAULT NULL,
       `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (`integral_deduction_info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for czb_integral_exchange
-- ----------------------------
DROP TABLE IF EXISTS `czb_integral_exchange`;
CREATE TABLE `czb_integral_exchange` (
    `integral_exchange` varchar(20) NOT NULL,
    `exchange_code` varchar(20) NOT NULL,
    `exchange_amount` int(8) NOT NULL,
    `exchange_times_total` int(8) NOT NULL,
    `exchange_times_current` int(8) NOT NULL,
    `exchange_type` int(8) NOT NULL,
    `exchange_source_id` varchar(20) NOT NULL,
    `is_complete` int(1) NOT NULL,
       `failure_time` timestamp NULL DEFAULT NULL,
       `create_at` timestamp NULL DEFAULT NULL,
       `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (`integral_exchange`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- ----------------------------
-- Table structure for czb_integral_exchange_log_id
-- ----------------------------
DROP TABLE IF EXISTS `czb_integral_exchange_log_id`;
CREATE TABLE `czb_integral_exchange_log_id` (
        `integral_exchange_log_id` varchar(20) NOT NULL,
        `integral_exchange_id` varchar(20) NOT NULL,
        `exchange_user_id` int(8) NOT NULL,
        `create_at` timestamp NULL DEFAULT NULL,
        `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
        PRIMARY KEY (`integral_exchange_log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
