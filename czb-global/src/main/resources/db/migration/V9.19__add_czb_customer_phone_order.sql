DROP TABLE IF EXISTS `czb_customer_phone_order`;
CREATE TABLE `czb_customer_phone_order` (
    `id` varchar(124) NOT NULL COMMENT 'id',
    `order_id` varchar(124) DEFAULT NULL COMMENT '订单id',
    `commodity_id` varchar(124) NULL DEFAULT NULL COMMENT '商品id',
    `rechargeAccount` varchar(124) NULL DEFAULT NULL COMMENT '充值账号',
    `rechargeAmount` double(10,2) NULL DEFAULT NULL COMMENT '充值金额',
    `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
