alter table czb_msg_model add msg_type INT( 1 ) DEFAULT 0 COMMENT '0无状态，1有状态' ;
alter table czb_msg_model add end_time TIMESTAMP NULL COMMENT '截止时间';
alter table czb_msg_model add altert INT( 1 ) DEFAULT 0 COMMENT '0为无，1为有';
alter table czb_msg_model add receiver_type INT( 1 ) DEFAULT 0 COMMENT '0全体，1普通用户， 2企业用户， 3合伙人';