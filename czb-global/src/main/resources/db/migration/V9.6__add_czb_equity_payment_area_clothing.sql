DROP TABLE IF EXISTS `czb_equity_payment_area_clothing`;
CREATE TABLE `czb_equity_payment_area_clothing` (
        `area_id` varchar(20) NOT NULL COMMENT '区服id',
        `game_name` varchar(20) DEFAULT NULL COMMENT '游戏名称',
        `area_name` varchar(20) NULL DEFAULT NULL COMMENT '区服名称',
        `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
        `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
        PRIMARY KEY (`area_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
