alter table czb_commodity add is_pay INT( 1 ) DEFAULT 0 COMMENT '0不支付，1支付 ';
alter table czb_vehicle_clean_order add user_name varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '车主名';
alter table czb_vehicle_clean_order add license_number varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '车牌号';
alter table czb_vehicle_clean_order add vehicle_color varchar (10) character  set utf8 collate utf8_general_ci null default null COMMENT '车辆颜色';
alter table czb_vehicle_clean_order add vehicle_series varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '车系';
alter table czb_vehicle_clean_order add service_location varchar (255) character  set utf8 collate utf8_general_ci null default null COMMENT '服务地点';
alter table czb_vehicle_clean_order add phone varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '联系人号码';






