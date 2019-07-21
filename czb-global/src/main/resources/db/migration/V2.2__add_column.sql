alter table czb_subsidy_mission add money_type TINYINT(2) COMMENT '是否是指定的金额';

alter table czb_commodity add is_examine TINYINT(2) COMMENT '是否审核，0 未审核 1 已审核';