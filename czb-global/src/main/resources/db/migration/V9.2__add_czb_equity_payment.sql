DROP TABLE IF EXISTS `czb_equity_payment_commodity`;
CREATE TABLE `czb_equity_payment_commodity` (
    `goods_id` varchar(20) NOT NULL COMMENT '商品id',
    `trade_type` int(8) DEFAULT NULL COMMENT '商品类型（充值类型 【卡密，直充】）',
    `type_id` varchar(20) NULL DEFAULT NULL COMMENT '商品所属类别id（关联类别表）',
    `goods_title` varchar(20) NULL DEFAULT NULL COMMENT '商品名称',
    `goods_type` int(8) NULL DEFAULT NULL COMMENT '分类所卖商品类型（1:视频；2:游戏）',
    `product_code` varchar(20) NULL DEFAULT NULL COMMENT '商品编码',
    `purchase_price` double(10,2) NULL DEFAULT NULL COMMENT '进价',
    `selling_price` double (10,2) NULL DEFAULT NULL COMMENT '售价',
    `goods_pic` varchar(50) NULL DEFAULT NULL COMMENT '商品图片',
    `unit_price` int(8) NULL DEFAULT NULL COMMENT '商品面值',
    `is_game` int(1) NULL DEFAULT NULL COMMENT '是否是游戏（为1时需要传入区服信息）',
    `desc` varchar(1000) NULL DEFAULT NULL COMMENT '描述信息',
    `on_sale` int(1) NULL DEFAULT NULL COMMENT '是否开售',
    `order` int(8) NULL DEFAULT NULL COMMENT '商品显示排序字段',
    `is_hot` int(8) NULL DEFAULT NULL COMMENT '是否热门',
    `is_delete` int(8) NULL DEFAULT NULL COMMENT '是否删除',
    `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


DROP TABLE IF EXISTS `czb_equity_payment_category`;
CREATE TABLE `czb_equity_payment_category` (
    `category_id` varchar(20) NOT NULL COMMENT '类目id',
    `category_name` varchar(20) DEFAULT NULL COMMENT '类目名称',
    `order` varchar(20) NULL DEFAULT NULL COMMENT '类目显示排序字段',
    `is_hot` int(8) NULL DEFAULT NULL COMMENT '是否热门',
    `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


DROP TABLE IF EXISTS `czb_equity_payment_type`;
CREATE TABLE `czb_equity_payment_type` (
    `type_id` varchar(20) NOT NULL COMMENT '类别id',
    `category_id` varchar(20) DEFAULT NULL COMMENT '类目id',
    `type_name` varchar(20) NULL DEFAULT NULL COMMENT '商品类别名称',
    `pic` varchar(50) NULL DEFAULT NULL COMMENT '商品类别图片',
    `order` int(8) NULL DEFAULT NULL COMMENT '类别显示排序字段',
    `is_hot` int(8) NULL DEFAULT NULL COMMENT '是否热门',
    `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`type_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


DROP TABLE IF EXISTS `czb_equity_payment_order`;
CREATE TABLE `czb_equity_payment_order` (
    `order_id` varchar(20) NOT NULL COMMENT '订单id',
    `user_id` varchar(20) DEFAULT NULL COMMENT '用户id',
    `goods_id` varchar(20) NULL DEFAULT NULL COMMENT '商品id',
    `pay_price` double(10,2) NULL DEFAULT NULL COMMENT '支付价格',
    `third_order` varchar(20) NULL DEFAULT NULL COMMENT '第三方订单号',
    `pay_method` int(8) NULL DEFAULT NULL COMMENT '支付状态',
    `order_state` int(8) NULL DEFAULT NULL COMMENT '支付方式',
    `recharge_account` varchar(20) NULL DEFAULT NULL COMMENT '充值帐户',
    `order_state` int(8) NULL DEFAULT NULL COMMENT '订单状态',
    `product_code` varchar(20) NULL DEFAULT NULL COMMENT '订单状态',
    `client_ip` varchar(20) NULL DEFAULT NULL COMMENT '客户端IP（区服信息）',
    `buy_num` int(8) NULL DEFAULT NULL COMMENT '购买数量',
    `code` varchar(100) NULL DEFAULT NULL COMMENT '卡密 / 链接',
    `create_at` timestamp NULL DEFAULT NULL COMMENT '创建时间',
    `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
