-- ----------------------------
-- Table structure for czb_offline_distributor_records
-- ----------------------------
alter table czb_user_income_info add offline_recharge_balance  double(10,2)  DEFAULT 0.0 COMMENT '线下充值余额';