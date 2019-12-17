alter table czb_msg_record add content  varchar(255)  DEFAULT null COMMENT '消息记录内容';
alter TABLE czb_partner_vip_income DROP COLUMN end_time;
alter table czb_partner_vip_income add end_time timestamp(0) NULL DEFAULT NULL;