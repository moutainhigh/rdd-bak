alter table czb_petrol_sales_records add denomination double(10,2)  DEFAULT 0.0 COMMENT '销售面额';
alter table czb_petrol_sales_records add current_price double(10,2)  DEFAULT 0.0 COMMENT '当前售价';