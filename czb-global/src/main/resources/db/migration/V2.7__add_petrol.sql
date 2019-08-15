alter table czb_petrol add remark varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '油卡备注';
alter table czb_server_standard add file_id varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '图片id';
alter table czb_vehicle_clean_order add server_id varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '服务号';
alter table czb_coupon_standard add is_delete INT( 1 ) DEFAULT 0 COMMENT '0不删除，1是删除';
alter table czb_rider_evaluate add evaluate_user_id varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '评价人id';
alter table czb_server_standard add service_location varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '服务地点';






