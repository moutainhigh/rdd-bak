alter table czb_wechat_commodity add item_no int(3);

alter table czb_wechat_commodity add constraint uni unique (item_no);