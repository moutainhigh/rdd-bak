alter table czb_dish_order add is_delete INTEGER( 1 ) DEFAULT 0 COMMENT '0不删除,1删除';
alter table czb_dish add is_delete INTEGER( 1 ) DEFAULT 0 COMMENT '0不删除,1删除';