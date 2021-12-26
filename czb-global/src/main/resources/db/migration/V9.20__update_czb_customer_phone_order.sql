ALTER TABLE czb_direct_charging_order ADD COLUMN `customer_order_id` varchar(124) NULL DEFAULT NULL COMMENT '客户订单号';

ALTER TABLE czb_customer_phone_order ADD COLUMN `our_order_id` varchar(124) NULL DEFAULT NULL COMMENT '我们订单号';
