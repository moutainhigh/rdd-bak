alter table czb_income_log add is_settlement INTEGER( 1 ) DEFAULT 0 COMMENT '0不结算，1结算';
alter table czb_income_log add partner_commission_level INTEGER( 1 ) DEFAULT 0 COMMENT '合伙人返佣等级0,1,2';
