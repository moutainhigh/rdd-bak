ALTER TABLE czb_h5_stock ADD COLUMN `record_type` int(10) NULL DEFAULT NULL COMMENT '订单类型';

ALTER TABLE czb_h5_stock ADD COLUMN `user_id` varchar(20) NULL DEFAULT NULL COMMENT '购买人';

ALTER TABLE czb_h5_stock ADD COLUMN `price` double(10, 2) NULL DEFAULT NULL COMMENT '价格';

ALTER TABLE czb_h5_stock ADD COLUMN `pay_price` double(10, 2) NULL DEFAULT NULL COMMENT '支付价格';

DROP TABLE IF EXISTS `czb_h5_commodity`;
CREATE TABLE `czb_h5_commodity` (
    `commodity_id` varchar(20) NOT NULL COMMENT '商品id',
    `commodity_title` varchar(20) NULL DEFAULT NULL COMMENT '商品名称',
    `is_delete` int(8) NULL DEFAULT NULL COMMENT '是否删除',
    `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`commodity_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

