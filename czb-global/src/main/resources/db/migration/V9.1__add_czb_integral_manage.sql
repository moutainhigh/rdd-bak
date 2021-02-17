DROP TABLE IF EXISTS `czb_integral_manage`;
CREATE TABLE `czb_integral_manage` (
    `id` varchar(20) NOT NULL,
    `denomination` int(8) DEFAULT NULL,
    `state` int(8) NULL DEFAULT NULL,
    `create_at` timestamp NULL DEFAULT NULL,
    `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
