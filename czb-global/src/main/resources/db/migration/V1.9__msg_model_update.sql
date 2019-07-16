alter table czb_msg_model add msg_type INT( 1 ) DEFAULT 0 COMMENT '0无状态，1有状态' ;
alter table czb_msg_model add endTime TIMESTAMP NULL COMMENT '截止时间';
alter table czb_msg_model add altert INT( 1 ) DEFAULT 0 COMMENT '0为无，1为有';