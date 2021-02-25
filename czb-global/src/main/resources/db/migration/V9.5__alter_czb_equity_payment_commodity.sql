ALTER TABLE czb_equity_payment_commodity DROP COLUMN purchase_price;

ALTER TABLE czb_equity_payment_commodity ADD COLUMN current_price double (10,2) NULL DEFAULT NULL COMMENT '现价(折扣价)';


