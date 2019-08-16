alter table czb_clean_server_vehicle add phone varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '联系人电话';
alter table czb_rider_evaluate add server_order_id varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '订单id';
alter table czb_vehicle_clean_order add pay_method INT( 1 ) DEFAULT 0 COMMENT '1支付宝，2微信'






