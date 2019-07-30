alter table czb_user drop column version_num;

alter table czb_user_login_info add user_id varchar (20) character  set utf8 collate utf8_general_ci null default null COMMENT '用户id';
