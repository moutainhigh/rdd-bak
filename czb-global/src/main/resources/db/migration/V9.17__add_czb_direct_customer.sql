DROP TABLE IF EXISTS `czb_direct_customer`;
CREATE TABLE `czb_direct_customer` (
    `customer_id` varchar(124) NOT NULL COMMENT '客户id',
    `app_id` varchar(124) DEFAULT NULL COMMENT 'appid',
    `app_secret` varchar(124) NULL DEFAULT NULL COMMENT 'appsecret',
    `customer_number` varchar(124) NULL DEFAULT NULL COMMENT '编号',
    `recharge_amount` double(10,2) NULL DEFAULT NULL COMMENT '充值金额',
    `balance` double(10,2) NULL DEFAULT NULL COMMENT '余额',
    `consumption_amount` double(10,2) NULL DEFAULT NULL COMMENT '消费金额',
    `amount_recovered` double(10,2) NULL DEFAULT NULL COMMENT '圈回金额',
    `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


ALTER TABLE czb_direct_charging_order ADD COLUMN `customer_number` varchar(124) NULL DEFAULT NULL COMMENT '合伙人编号';

