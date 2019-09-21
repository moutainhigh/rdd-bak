-- 删除字段
alter table czb_commodity drop column commmodity_type,drop column longitude,drop column latitude,drop column starting_time_business,drop column end_time_business;
-- 增加字段
alter table czb_shop add shop_type INT( 1 ) DEFAULT 0 COMMENT '商店类型';
alter table czb_shop add longitude double(10,2)  DEFAULT 0.0 COMMENT '经纬度';
alter table czb_shop add latitude double(10,2)  DEFAULT 0.0 COMMENT '经纬度';
ALTER TABLE czb_shop ADD starting_time_business TIMESTAMP NULL COMMENT '营业开始时间';
ALTER TABLE czb_shop ADD end_time_business TIMESTAMP NULL COMMENT '营业结束时间';
alter table czb_shop add shop_img varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '商店图片';
alter table czb_dish change commodity_id shop_id varchar (20);