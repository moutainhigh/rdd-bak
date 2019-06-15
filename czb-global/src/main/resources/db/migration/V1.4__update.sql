/*
Navicat MySQL Data Transfer

Source Server         : 39.98.250.45_3306
Source Server Version : 50725
Source Host           : 39.98.250.45:3306
Source Database       : czb

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-27 20:48:20
*/

ALTER TABLE czb_user ADD mission_start_time TIMESTAMP NULL COMMENT '任务开始时间';
ALTER TABLE czb_user ADD mission_end_time TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '任务结束时间';
ALTER TABLE czb_user ADD partner INT ( 1 ) DEFAULT 0 COMMENT '合伙人(0 不是合伙人 1 事业合伙人 2 普通合伙人)';

ALTER TABLE czb_user_income_info ADD total_consumption double ( 10,2 ) DEFAULT 0 COMMENT '个人总消费金额';

ALTER TABLE czb_petrol_sales_records MODIFY `record_id` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for czb_indicator_record
-- ----------------------------
DROP TABLE IF EXISTS `czb_indicator_record`;
CREATE TABLE `czb_indicator_record`  (
  `record_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录ID',
  `target_promotion_number` int(5) NULL DEFAULT NULL COMMENT '目标推广人数',
  `actual_promotion_number` int(5) NULL DEFAULT NULL COMMENT '实际推广人数',
  `target_new_consumer` int(5) NULL DEFAULT NULL COMMENT '目标新增消费人数',
  `actual_new_consumer` int(5) NULL DEFAULT NULL COMMENT '实际新增消费人数',
  `state` int(1) NULL DEFAULT NULL COMMENT '指标状态',
  `indicator_begin_time` timestamp(0) NULL DEFAULT NULL COMMENT '指标开始时间',
  `indicator_end_time` timestamp(0) NULL DEFAULT NULL COMMENT '指标结束时间',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指标人',
  `create_at` timestamp(0) NULL DEFAULT NULL,
  `update_at` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for czb_consumption_record
-- ----------------------------
DROP TABLE IF EXISTS `czb_consumption_record`;
CREATE TABLE `czb_consumption_record`  (
  `record_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录ID',
  `local_order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '本地订单号',
  `third_order_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方订单号',
  `money` double(10, 2) NULL DEFAULT NULL COMMENT '消费金额',
  `type` int(1) NULL DEFAULT NULL COMMENT '消费类型（0：油卡购买）',
  `user_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '消费人',
  `original_amount` double(10, 2) NULL DEFAULT NULL COMMENT '原始金额',
  `pay_method` int(1) NULL DEFAULT NULL COMMENT '支付方式（1：支付宝）',
  `create_at` timestamp(0) NULL DEFAULT NULL,
  `update_at` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
