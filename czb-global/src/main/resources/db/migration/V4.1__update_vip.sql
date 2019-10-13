alter table czb_vip_area_config add fy_rate double(10,2)  DEFAULT 0.0 COMMENT '返佣比例';
alter table czb_vip_area_config add fy_one double(10,2)  DEFAULT 0.0 COMMENT '一级返佣';
alter table czb_vip_area_config add fy_two double(10,2)  DEFAULT 0.0 COMMENT '二级返佣';