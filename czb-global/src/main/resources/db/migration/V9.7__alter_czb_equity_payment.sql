ALTER TABLE czb_equity_payment_area_clothing ADD COLUMN `product_code` varchar(20) NULL DEFAULT NULL COMMENT '商品编码';

ALTER TABLE czb_equity_payment_category MODIFY COLUMN `order` int(8) NULL DEFAULT NULL COMMENT '类目显示排序字段';

ALTER TABLE czb_equity_payment_commodity ADD COLUMN `product_details` varchar(100) NULL DEFAULT NULL COMMENT '商品详细信息';