alter table czb_auto_recharge_record add user_id varchar(20) character  set utf8 collate utf8_general_ci null default null COMMENT '用户id';
alter table czb_auto_recharge_record add user_name varchar(255) character  set utf8 collate utf8_general_ci null default null COMMENT '用户名';