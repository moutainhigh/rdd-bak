ALTER TABLE czb_petrol_coupons_sales_records MODIFY COLUMN order_info varchar(1000);
alter table czb_petrol_coupons_sales_records add lu_pay_balance  double(10,2)  DEFAULT 0.0 COMMENT '璐付余额';