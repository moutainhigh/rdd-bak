alter table czb_dish_order drop column commodity_id;
alter table czb_dish_order add dish_id VARCHAR( 20 ) character  set utf8 collate utf8_general_ci null default null COMMENT '菜品ID';
alter table czb_dish add is_recommend INTEGER( 1 ) DEFAULT 0 COMMENT '是否推荐';