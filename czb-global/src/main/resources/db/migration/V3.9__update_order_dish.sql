alter table czb_order_dishes add dish_count INTEGER( 10 ) DEFAULT 0 COMMENT '菜品数量';
alter table czb_shop add order_write_off INTEGER( 1 ) DEFAULT 0 COMMENT '订单是否核销 0不核销，1核销';
alter table czb_dish add dish_content varchar(255) character  set utf8 collate utf8_general_ci null default null COMMENT '菜品内容';