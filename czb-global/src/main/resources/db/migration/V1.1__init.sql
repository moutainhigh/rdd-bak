/*
Navicat MySQL Data Transfer

Source Server         : 39.98.250.45_3306
Source Server Version : 50725
Source Host           : 39.98.250.45:3306
Source Database       : czb_temp

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-26 12:06:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for czb_address
-- ----------------------------
DROP TABLE IF EXISTS `czb_address`;
CREATE TABLE `czb_address` (
  `address_id` varchar(20) NOT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `contact_number` varchar(20) DEFAULT NULL,
  `receiver` varchar(20) DEFAULT NULL,
  `is_default` int(1) DEFAULT NULL,
  PRIMARY KEY (`address_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_address
-- ----------------------------

-- ----------------------------
-- Table structure for czb_announcement
-- ----------------------------
DROP TABLE IF EXISTS `czb_announcement`;
CREATE TABLE `czb_announcement` (
  `announcement_id` varchar(20) NOT NULL,
  `announcement_title` varchar(20) DEFAULT NULL,
  `announcement_content` varchar(10000) DEFAULT NULL,
  `announcement_type` int(1) DEFAULT NULL,
  `img_file_id` varchar(20) DEFAULT NULL,
  `is_show` int(1) DEFAULT NULL,
  `announcement_order` int(2) DEFAULT NULL,
  `location_code` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`announcement_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_announcement
-- ----------------------------

-- ----------------------------
-- Table structure for czb_app_router
-- ----------------------------
DROP TABLE IF EXISTS `czb_app_router`;
CREATE TABLE `czb_app_router` (
  `router_id` varchar(20) NOT NULL,
  `path_type` int(1) DEFAULT NULL,
  `menu_name` varchar(20) DEFAULT NULL,
  `is_show` int(1) DEFAULT NULL,
  `icon_path_id` varchar(20) DEFAULT NULL,
  `menu_identity_code` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `android_path` varchar(100) DEFAULT NULL,
  `ios_path` varchar(100) DEFAULT NULL,
  `order` int(2) DEFAULT NULL,
  `user_type` int(1) DEFAULT NULL,
  PRIMARY KEY (`router_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_app_router
-- ----------------------------
INSERT INTO `czb_app_router` VALUES ('155809421554033', '0', '油卡服务', '1', '155809421539682', 'homepageNav', '2019-05-17 19:56:56', '2019-05-25 21:52:54', '/oilCard/OilCardAccount', 'PurchaseOilCard', '2', '0');
INSERT INTO `czb_app_router` VALUES ('155825337292114', '0', '分享赚钱', '1', '155824860431429', 'homepageNav', '2019-05-19 16:09:33', '2019-05-25 21:13:51', '/mine/shareToEarn/shareToEarnActivity', 'share', '1', '0');
INSERT INTO `czb_app_router` VALUES ('155825579297947', '1', '我的油卡', '1', '155825579202639', 'personalCenter', '2019-05-19 16:49:53', '2019-05-24 16:52:01', '/mine/myOilCard/myOilCardActivity', 'oilCard', '1', '0');
INSERT INTO `czb_app_router` VALUES ('155825586899998', '1', '收款记录', '1', '155825586877339', 'personalCenter', '2019-05-19 16:51:09', '2019-05-24 16:52:27', '/mine/recordCollection/RecordCollectionActivity', 'CollectionRecord', '7', '0');
INSERT INTO `czb_app_router` VALUES ('155825596445841', '1', '设置', '1', '155825596423199', 'personalCenter', '2019-05-19 16:52:44', '2019-05-23 09:44:00', '/mine/setting/settingActivity', 'setting', null, null);
INSERT INTO `czb_app_router` VALUES ('155825642098880', '1', '油码信息', '1', '155825642083472', 'personalCenter', '2019-05-19 17:00:21', '2019-05-23 09:44:24', '/mine/myOilCard/oilCardRecordActivity', 'oilCode', null, '1');
INSERT INTO `czb_app_router` VALUES ('155825653791631', '1', '打款记录', '1', '155825653769962', 'personalCenter', '2019-05-19 17:02:18', '2019-05-23 09:44:31', '/mine/recordCollection/RecordCollectionActivity', 'CollectionRecord', null, '1');
INSERT INTO `czb_app_router` VALUES ('155825690212688', '1', '修改联系人', '1', '155825690190828', 'personalCenter', '2019-05-19 17:08:22', '2019-05-23 09:44:45', '/mine/modifyAccount/modifyContactActivity', 'editContact', null, '1');
INSERT INTO `czb_app_router` VALUES ('155827301333904', '0', '爆款商品', '1', '155827301305853', 'homepageActivity', '2019-05-19 21:36:53', '2019-05-23 13:27:56', '/chezubao/home/buyCardWithTicket', '/', '2', '0');
INSERT INTO `czb_app_router` VALUES ('155844447985972', '1', '企业带票购油', '1', '155844447964345', 'homepageActivity', '2019-05-21 21:14:40', '2019-05-23 12:34:30', '/chezubao/home/buyCardWithTicket', 'enterpriseWithTicketPurchaseOil', '1', '1');
INSERT INTO `czb_app_router` VALUES ('155860255425804', '0', '测试', '0', '155860255416105', '1', '2019-05-23 17:09:14', '2019-05-23 17:09:14', '1', '1', null, null);
INSERT INTO `czb_app_router` VALUES ('155868556022359', '0', '96', '0', '155868556010351', '69', '2019-05-24 16:12:40', '2019-05-24 16:12:40', '69', '69', null, null);
INSERT INTO `czb_app_router` VALUES ('155879023498301', '1', '分享赚钱', '1', '155879023472372', 'personalCenter', '2019-05-25 21:17:15', '2019-05-25 21:18:55', '/mine/shareToEarn/shareToEarnActivity', 'share', '2', '0');
INSERT INTO `czb_app_router` VALUES ('155879031785755', '1', '分享赚钱', '1', '155879031761271', 'personalCenter', '2019-05-25 21:18:38', '2019-05-25 21:18:58', '/mine/shareToEarn/shareToEarnActivity', 'share', '2', '1');
INSERT INTO `czb_app_router` VALUES ('3', '0', '使用说明', '1', '155730242909134', 'homepageNav', '2019-05-19 11:14:29', '2019-05-24 15:35:35', '/', '/', '3', '0');
INSERT INTO `czb_app_router` VALUES ('4', '0', '油卡服务', '1', '155809421539682', 'homepageNav', '2019-05-17 19:56:56', '2019-05-25 23:17:28', '/oilCard/OilCardAccount', 'PurchaseOilCard', '2', '1');
INSERT INTO `czb_app_router` VALUES ('7', '0', '爆款商品', '1', '155827301305853', 'homepageActivity', '2019-05-19 21:36:53', '2019-05-23 13:27:54', '/chezubao/home/buyCardWithTicket', '/', '2', '1');

-- ----------------------------
-- Table structure for czb_cars_persons
-- ----------------------------
DROP TABLE IF EXISTS `czb_cars_persons`;
CREATE TABLE `czb_cars_persons` (
  `person_car_id` varchar(20) NOT NULL,
  `person_name` varchar(20) DEFAULT NULL,
  `person_id_card` varchar(20) DEFAULT NULL,
  `car_model` varchar(20) DEFAULT NULL,
  `car_license` varchar(20) DEFAULT NULL,
  `contract_record_id` varchar(20) DEFAULT NULL,
  `plan_id` varchar(20) DEFAULT NULL,
  `petrol_type` int(1) DEFAULT NULL,
  `is_signed` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `identify_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`person_car_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_cars_persons
-- ----------------------------

-- ----------------------------
-- Table structure for czb_contract_model
-- ----------------------------
DROP TABLE IF EXISTS `czb_contract_model`;
CREATE TABLE `czb_contract_model` (
  `model_id` varchar(20) NOT NULL,
  `yun_model_id` varchar(50) DEFAULT NULL,
  `file_id` varchar(200) DEFAULT NULL,
  `model_name` varchar(50) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`model_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_contract_model
-- ----------------------------

-- ----------------------------
-- Table structure for czb_contract_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_contract_records`;
CREATE TABLE `czb_contract_records` (
  `record_id` varchar(20) NOT NULL,
  `signed_time` timestamp NULL DEFAULT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `contract_start_time` timestamp NULL DEFAULT NULL,
  `contract_end_time` timestamp NULL DEFAULT NULL,
  `contract_state` int(1) DEFAULT NULL,
  `contract_type` int(1) DEFAULT NULL,
  `rent` double(10,0) DEFAULT NULL,
  `third_contract_num` varchar(20) DEFAULT NULL,
  `got_total_money` double(10,0) DEFAULT NULL,
  `judge_user_id` varchar(20) DEFAULT NULL,
  `judge_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `pay_total_money` double(10,0) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `contract_father_id` varchar(20) DEFAULT NULL,
  `witness_id` varchar(20) DEFAULT NULL COMMENT '合同存证id',
  `bank_of_deposit` varchar(255) DEFAULT NULL,
  `bank_account_num` varchar(30) DEFAULT NULL,
  `bank_user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_contract_records
-- ----------------------------

-- ----------------------------
-- Table structure for czb_contract_yun_info
-- ----------------------------
DROP TABLE IF EXISTS `czb_contract_yun_info`;
CREATE TABLE `czb_contract_yun_info` (
  `yun_contract_id` varchar(50) NOT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `contract_id` varchar(50) DEFAULT NULL,
  `cz_id` varchar(50) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`yun_contract_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_contract_yun_info
-- ----------------------------

-- ----------------------------
-- Table structure for czb_deposit_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_deposit_records`;
CREATE TABLE `czb_deposit_records` (
  `record_id` varchar(20) NOT NULL,
  `payer` varchar(20) DEFAULT NULL,
  `pay_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `amount` double(10,2) DEFAULT NULL,
  `is_recharged` int(1) DEFAULT NULL,
  `payer_id` varchar(20) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `user_id_card` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_deposit_records
-- ----------------------------

-- ----------------------------
-- Table structure for czb_dict
-- ----------------------------
DROP TABLE IF EXISTS `czb_dict`;
CREATE TABLE `czb_dict` (
  `dict_id` varchar(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_dict
-- ----------------------------
INSERT INTO `czb_dict` VALUES ('1', 'kf1', '{\'name\': \'小雪\', \'mobile\': \'15723354978\'} ', '2019-05-12 01:36:33', '2019-05-18 20:38:27');
INSERT INTO `czb_dict` VALUES ('10', 'fangyong_rate', '0.10', '2019-05-19 16:54:02', '2019-05-25 13:09:07');
INSERT INTO `czb_dict` VALUES ('11', 'petrolRemark', '{\'0\': \'国通\', \'1\': \'中石油\', \'2\': \'中石化\'} ', '2019-05-23 16:09:22', '2019-05-25 20:51:54');
INSERT INTO `czb_dict` VALUES ('2', 'fangyong1', '0.10', '2019-05-13 20:37:59', '2019-05-19 16:55:24');
INSERT INTO `czb_dict` VALUES ('3', 'fangyong2', '0.05', '2019-05-13 20:38:45', '2019-05-19 16:55:29');
INSERT INTO `czb_dict` VALUES ('4', 'android_version', 'V1.0', '2019-05-14 17:29:07', '2019-05-25 21:39:02');
INSERT INTO `czb_dict` VALUES ('5', 'android_description', '1.0版本 101100111011 1001 0011 1111\r\n1111 1100 0001 1010\r\n1011 1001 0011 1111\r\n1111 1100 0001 1010\r\n1011 1001 0011 1111\r\n1111 1100 0001 10101111 1100 0001 1010\r\n1011 1001 0011 1111\r\n1111 1100 0001 1010', '2019-05-14 17:29:07', '2019-05-14 21:24:54');
INSERT INTO `czb_dict` VALUES ('6', 'android_url', 'https://www.pgyer.com/8dzr', '2019-05-14 17:29:07', '2019-05-25 21:33:20');
INSERT INTO `czb_dict` VALUES ('7', 'ios_version', 'V1.0', '2019-05-14 17:29:07', '2019-05-14 20:51:27');
INSERT INTO `czb_dict` VALUES ('8', 'ios_description', '1.0版本 101100111011 1001 0011 1111\r\n1111 1100 0001 1010\r\n1011 1001 0011 1111\r\n1111 1100 0001 1010\r\n1011 1001 0011 1111\r\n1111 1100 0001 10101111 1100 0001 1010\r\n1011 1001 0011 1111\r\n1111 1100 0001 1010', '2019-05-14 17:29:07', '2019-05-14 21:25:04');
INSERT INTO `czb_dict` VALUES ('9', 'ios_url', 'https://www.baidu.com', '2019-05-14 17:29:07', '2019-05-14 20:22:42');

-- ----------------------------
-- Table structure for czb_enterprise_consulting_info
-- ----------------------------
DROP TABLE IF EXISTS `czb_enterprise_consulting_info`;
CREATE TABLE `czb_enterprise_consulting_info` (
  `consulting_id` varchar(20) NOT NULL,
  `enterprise_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `contact_phone` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `is_handled` int(1) DEFAULT NULL,
  PRIMARY KEY (`consulting_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of czb_enterprise_consulting_info
-- ----------------------------

-- ----------------------------
-- Table structure for czb_enterprise_info
-- ----------------------------
DROP TABLE IF EXISTS `czb_enterprise_info`;
CREATE TABLE `czb_enterprise_info` (
  `enterprise_info_id` varchar(20) NOT NULL,
  `enterprise_name` varchar(20) DEFAULT NULL,
  `legal_person` varchar(20) DEFAULT NULL,
  `contact_info` varchar(20) DEFAULT NULL,
  `is_deleted` int(1) DEFAULT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `org_code` varchar(30) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `enterprise_yun_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`enterprise_info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_enterprise_info
-- ----------------------------

-- ----------------------------
-- Table structure for czb_file
-- ----------------------------
DROP TABLE IF EXISTS `czb_file`;
CREATE TABLE `czb_file` (
  `file_id` varchar(20) NOT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `uploader` varchar(20) DEFAULT NULL,
  `save_path` varchar(500) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_file
-- ----------------------------
INSERT INTO `czb_file` VALUES ('1', 'nihao', '1', 'http://www.paochefang.com/wp-content/uploads/paoimage/2013/07/014807keN.jpg', 'wqe', '2019-05-14 15:03:04', '2019-05-08 01:06:03');
INSERT INTO `czb_file` VALUES ('155730242909134', '1.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155824842853523%E7%BB%84%203%402x.png?Expires=4711848428&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=n/Ja96gh3MzF4kYF/ZsVJWvvQxI%3D', null, '2019-05-08 16:00:29', '2019-05-19 14:47:09');
INSERT INTO `czb_file` VALUES ('155730372726974', '2.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557303726816832.jpg?Expires=4710903727&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=UElE8oIk2Kzy1dy6cH1ZTQ5hX0c%3D', null, '2019-05-08 16:22:07', '2019-05-08 16:22:07');
INSERT INTO `czb_file` VALUES ('155730744793887', '1.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557307446788111.jpg?Expires=4710907447&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=y5iI5UKM3ZnZWeHoT8QSo3sHgck%3D', null, '2019-05-08 17:24:08', '2019-05-08 17:24:08');
INSERT INTO `czb_file` VALUES ('155738800491747', '1.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557388004567891.jpg?Expires=4710988004&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=CajQ7kUkQWDQdRlJ0RxFfhECves%3D', null, '2019-05-09 07:46:45', '2019-05-09 07:46:45');
INSERT INTO `czb_file` VALUES ('155738812736581', '1.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557388127213241.jpg?Expires=4710988127&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=j1oN02emN/V9M1dKKEmpURImCjA%3D', null, '2019-05-09 07:48:47', '2019-05-09 07:48:47');
INSERT INTO `czb_file` VALUES ('155738824716140', '1.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557388247018921.jpg?Expires=4710988247&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=TaFchOeyQihzBxKqr9pZEvZ81PI%3D', null, '2019-05-09 07:50:47', '2019-05-09 07:50:47');
INSERT INTO `czb_file` VALUES ('155738829543989', '2c6bee4cc6decf8170756b81c009821b.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557388294171032c6bee4cc6decf8170756b81c009821b.jpg?Expires=4710988295&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=D2EtM/%2BwwN/6V669%2B/FNZC5jCHU%3D', null, '2019-05-09 07:51:35', '2019-05-09 07:51:35');
INSERT INTO `czb_file` VALUES ('155738829736787', '2c6bee4cc6decf8170756b81c009821b.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557388294706902c6bee4cc6decf8170756b81c009821b.jpg?Expires=4710988297&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=GL1u5h/oDnpT0yLmGUx4kDfJlvY%3D', null, '2019-05-09 07:51:37', '2019-05-09 07:51:37');
INSERT INTO `czb_file` VALUES ('155738846725170', '2.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557388467078382.jpg?Expires=4710988467&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=6M3Bn15z/0IaeWwj3NrJDsMJm8Q%3D', null, '2019-05-09 07:54:27', '2019-05-09 07:54:27');
INSERT INTO `czb_file` VALUES ('155738851918447', '2c6bee4cc6decf8170756b81c009821b.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557388518009152c6bee4cc6decf8170756b81c009821b.jpg?Expires=4710988519&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=NNq68Oog8MbPF/EhAniHqJ3ttiQ%3D', null, '2019-05-09 07:55:19', '2019-05-09 07:55:19');
INSERT INTO `czb_file` VALUES ('155738914475255', '0311bab1bfc81c0eb20884383788967b.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557389144426890311bab1bfc81c0eb20884383788967b.jpg?Expires=4710989144&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=TCDJS/shtq%2BNWwvFmaPd2DBpQhs%3D', null, '2019-05-09 08:05:45', '2019-05-09 08:05:45');
INSERT INTO `czb_file` VALUES ('155738925514405', '2c6bee4cc6decf8170756b81c009821b.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557389253882512c6bee4cc6decf8170756b81c009821b.jpg?Expires=4710989255&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=ljHPky0%2BxlQB18MKG6u7WrxKb/Y%3D', null, '2019-05-09 08:07:35', '2019-05-09 08:07:35');
INSERT INTO `czb_file` VALUES ('155739031772786', '1.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557390317590451.jpg?Expires=4710990317&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=3sUUAtR0Zv6vpVysnb3ZuveZxo8%3D', null, '2019-05-09 08:25:18', '2019-05-09 08:25:18');
INSERT INTO `czb_file` VALUES ('155739064261768', '3.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557390642399033.jpg?Expires=4710990642&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=aZmbF0ya8aGqBEJsNHFH%2BcVTEnU%3D', null, '2019-05-09 08:30:43', '2019-05-09 08:30:43');
INSERT INTO `czb_file` VALUES ('155739081932920', '1.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557390819204691.jpg?Expires=4710990819&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=PyhwfQ3EevLGAfsrx%2B8UNwe5ESE%3D', null, '2019-05-09 08:33:39', '2019-05-09 08:33:39');
INSERT INTO `czb_file` VALUES ('155739088521312', '5.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557754657267141.jpg?Expires=4711354657&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=Z%2BPVbYgUGZt3akwvRIXu6puarF0%3D', null, '2019-05-09 08:34:45', '2019-05-13 13:37:38');
INSERT INTO `czb_file` VALUES ('155739348598592', 'app菜单配置接口.docx', '155583812434620', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155739348572154app%E8%8F%9C%E5%8D%95%E9%85%8D%E7%BD%AE%E6%8E%A5%E5%8F%A3.docx?Expires=4710993485&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=hPQ20lxIDEfMnwze3XKdtnq2I/M%3D', null, '2019-05-09 09:18:06', '2019-05-09 09:18:06');
INSERT INTO `czb_file` VALUES ('155740080319042', 'u=394558962,2184217689&fm=27&gp=0.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155740080293550u%3D394558962%2C2184217689%26fm%3D27%26gp%3D0.jpg?Expires=4711000803&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=pNQZ2ijAp7XShRn6meLXHI6BKqo%3D', null, '2019-05-09 11:20:03', '2019-05-09 11:20:03');
INSERT INTO `czb_file` VALUES ('155740093361079', '3.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557400933419733.jpg?Expires=4711000933&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=wnmUQytNyD5qA5soM2rngBvF7Xw%3D', null, '2019-05-09 11:22:14', '2019-05-09 11:22:14');
INSERT INTO `czb_file` VALUES ('155764955071927', '尼尔森十项启发式原则.doc', '155583812434620', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155764955026548%E5%B0%BC%E5%B0%94%E6%A3%AE%E5%8D%81%E9%A1%B9%E5%90%AF%E5%8F%91%E5%BC%8F%E5%8E%9F%E5%88%99.doc?Expires=4711249550&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=u59addiqLth///%2BB0z7/OCBVtQU%3D', null, '2019-05-12 08:25:51', '2019-05-12 08:25:51');
INSERT INTO `czb_file` VALUES ('155764971578915', '新建 DOC 文档.doc', '155583812434620', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155764971552283%E6%96%B0%E5%BB%BA%20DOC%20%E6%96%87%E6%A1%A3.doc?Expires=4711249715&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=FyqRWT1/2iIqJfPE%2BTA8twYiDbg%3D', null, '2019-05-12 08:28:36', '2019-05-12 08:28:36');
INSERT INTO `czb_file` VALUES ('155764973236565', '新建 DOC 文档.doc', '155583812434620', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155764973225482%E6%96%B0%E5%BB%BA%20DOC%20%E6%96%87%E6%A1%A3.doc?Expires=4711249732&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=rzyNoa/0DGbWHVDIKyBNiz5NOpQ%3D', null, '2019-05-12 08:28:52', '2019-05-12 08:28:52');
INSERT INTO `czb_file` VALUES ('155780075139818', '9dcc3ef6ae0696534107ce2b13f81a03.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557800750960639dcc3ef6ae0696534107ce2b13f81a03.jpg?Expires=4711400751&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=Pq/mrgaU/2l8Zm7d0RElIiOCdJ8%3D', null, '2019-05-14 02:25:51', '2019-05-14 02:25:51');
INSERT INTO `czb_file` VALUES ('155780079185073', 'ChMkJld2O0GIPdSbAAtP7L6piRwAATIkgCY3TgAC1AE489.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155780078825124ChMkJld2O0GIPdSbAAtP7L6piRwAATIkgCY3TgAC1AE489.jpg?Expires=4711400791&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=KA7YCFB5UxBI42%2B45uni5nBXNwg%3D', null, '2019-05-14 02:26:32', '2019-05-14 02:26:32');
INSERT INTO `czb_file` VALUES ('155785068150970', 'TIM图片20190515001443.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155785068070755TIM%E5%9B%BE%E7%89%8720190515001443.jpg?Expires=4711450681&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=AoXg8z4dE6hxMQVQBOxjjtiVIXM%3D', null, '2019-05-15 00:18:02', '2019-05-15 00:18:02');
INSERT INTO `czb_file` VALUES ('155785078737535', 'TIM图片20190515001443.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155785078698492TIM%E5%9B%BE%E7%89%8720190515001443.jpg?Expires=4711450787&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=JVoCQjRk3NcnKeF9cAAIZoQXEUU%3D', null, '2019-05-15 00:19:47', '2019-05-15 00:19:47');
INSERT INTO `czb_file` VALUES ('155785083186792', 'TIM图片20190515001443.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155785083145467TIM%E5%9B%BE%E7%89%8720190515001443.jpg?Expires=4711450831&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=PPXxrm0J4274DZ4OvXib96kfSac%3D', null, '2019-05-15 00:20:32', '2019-05-15 00:20:32');
INSERT INTO `czb_file` VALUES ('155785414451493', 'TIM图片20190515001443.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155785414362984TIM%E5%9B%BE%E7%89%8720190515001443.jpg?Expires=4711454144&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=DadBoRnI694BdOLCf4Lr76ZsH/s%3D', null, '2019-05-15 01:15:45', '2019-05-15 01:15:45');
INSERT INTO `czb_file` VALUES ('155785421631736', 'TIM图片20190515001443.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155785421580618TIM%E5%9B%BE%E7%89%8720190515001443.jpg?Expires=4711454216&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=O1mXODfIgrG8%2BUMSlyjV3p96iVI%3D', null, '2019-05-15 01:16:56', '2019-05-15 01:16:56');
INSERT INTO `czb_file` VALUES ('155785435331149', 'TIM图片20190515001443.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155785435280613TIM%E5%9B%BE%E7%89%8720190515001443.jpg?Expires=4711454353&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=We3Om7F14TDcVbZbUjMQUTfCAZc%3D', null, '2019-05-15 01:19:13', '2019-05-15 01:19:13');
INSERT INTO `czb_file` VALUES ('155788481212286', '5555.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1557884803571065555.jpg?Expires=4711484812&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=v0mx5ImnHHKlKIKZFEjdsUlbIUY%3D', null, '2019-05-15 09:46:52', '2019-05-15 09:46:52');
INSERT INTO `czb_file` VALUES ('155809404493664', 'logo.png', '123456', '', null, '2019-05-17 19:54:05', '2019-05-19 13:24:46');
INSERT INTO `czb_file` VALUES ('155809421539682', 'logo.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155879744846246%E7%BB%84%203_2%402x.png?Expires=4712397449&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=gW5lfq%2B8U0pi3tX6x46Rs/E/R2o%3D', null, '2019-05-17 19:56:55', '2019-05-25 23:17:29');
INSERT INTO `czb_file` VALUES ('155816870693409', '1.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1558168706580571.jpg?Expires=4711768706&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=91oKsPDtp61CsbKA4gZuLktMqZY%3D', null, '2019-05-18 16:38:27', '2019-05-18 16:38:27');
INSERT INTO `czb_file` VALUES ('155824860431429', '组 3_1@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155879003139060%E7%BB%84%203_1%402x.png?Expires=4712390031&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=UCUFwPLLTMvV8NBdj6SgCpQ2KSs%3D', null, '2019-05-19 14:50:04', '2019-05-25 21:13:52');
INSERT INTO `czb_file` VALUES ('155824886842260', '组 3_1@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155824886810218%E7%BB%84%203_1%402x.png?Expires=4711848868&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=i76KMZyvfpyu3Yqdf6fBdxYqDuI%3D', null, '2019-05-19 14:54:28', '2019-05-19 14:54:28');
INSERT INTO `czb_file` VALUES ('155825030245276', '组 3_1@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155825030203671%E7%BB%84%203_1%402x.png?Expires=4711850302&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=k%2Bqko6ZopdoW3Yf8MIxedAznS68%3D', null, '2019-05-19 15:18:22', '2019-05-19 15:18:22');
INSERT INTO `czb_file` VALUES ('155825261051909', '图层 74.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155825260897597%E5%9B%BE%E5%B1%82%2074.png?Expires=4711852610&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=8OEmII2jFsLC3NHWtVbTr02f0HU%3D', null, '2019-05-19 15:56:51', '2019-05-19 15:56:51');
INSERT INTO `czb_file` VALUES ('155825298130238', '图层 74.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155825298094616%E5%9B%BE%E5%B1%82%2074.png?Expires=4711852981&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=qVihUWlMPBweom2eweP9B5eL3Pw%3D', null, '2019-05-19 16:03:01', '2019-05-19 16:03:01');
INSERT INTO `czb_file` VALUES ('155825337266391', '组 3_1@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1558685622740788e2560169476aaa4c82dffde6c995de6.jpg?Expires=4712285623&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=Y8sQPHpL4fUMQl4Av2UsVodSnIw%3D', null, '2019-05-19 16:09:33', '2019-05-24 16:13:43');
INSERT INTO `czb_file` VALUES ('155825579202639', '矩形 13 拷贝@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155825579174784%E7%9F%A9%E5%BD%A2%2013%20%E6%8B%B7%E8%B4%9D%402x.png?Expires=4711855792&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=vsAOCzUQHe2jTFzE2/bAHXXWQKk%3D', null, '2019-05-19 16:49:52', '2019-05-19 16:49:52');
INSERT INTO `czb_file` VALUES ('155825586877339', '矩形 17@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155825586849342%E7%9F%A9%E5%BD%A2%2017%402x.png?Expires=4711855868&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=SxMRKyjU4U8lPSbs8f7DTmr2X%2BA%3D', null, '2019-05-19 16:51:09', '2019-05-19 16:51:09');
INSERT INTO `czb_file` VALUES ('155825596423199', '椭圆 3@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155825596396825%E6%A4%AD%E5%9C%86%203%402x.png?Expires=4711855964&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=Xio/HpHciCh3XS22Vkcb0ciRTf0%3D', null, '2019-05-19 16:52:44', '2019-05-19 16:52:44');
INSERT INTO `czb_file` VALUES ('155825642083472', '矩形 13 拷贝 2@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155825719385311%E7%9F%A9%E5%BD%A2%2013%20%E6%8B%B7%E8%B4%9D%203%402x.png?Expires=4711857194&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=sWtt%2BgE2J9hkJE4MFA9/EQO8lLA%3D', null, '2019-05-19 17:00:21', '2019-05-19 17:13:14');
INSERT INTO `czb_file` VALUES ('155825653769962', '矩形 17@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155825711280808page7_%E6%89%93%E6%AC%BE%E8%AE%B0%E5%BD%95%402x.png?Expires=4711857113&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=NFFcO2f%2BcKrIn4VWC62xOBw6tzo%3D', null, '2019-05-19 17:02:18', '2019-05-19 17:11:53');
INSERT INTO `czb_file` VALUES ('155825690190828', '形状 15@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155825717418159%E5%BD%A2%E7%8A%B6%2015%402x.png?Expires=4711857174&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=7%2BoFdbLuRoFla1putC6T2nTZT%2Bo%3D', null, '2019-05-19 17:08:22', '2019-05-19 17:12:54');
INSERT INTO `czb_file` VALUES ('155827301305853', 'buy_with_ticket.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155827301176557buy_with_ticket.png?Expires=4711873013&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=a40AkHPg2FYIteZZh57jgVFjYQ4%3D', null, '2019-05-19 21:36:53', '2019-05-19 21:36:53');
INSERT INTO `czb_file` VALUES ('155827308322257', 'buy_card_with_ticket.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155827308213768buy_card_with_ticket.png?Expires=4711873083&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=Go0ytR1kt%2BZokoO/sS5FebTsp60%3D', null, '2019-05-19 21:38:03', '2019-05-19 21:38:03');
INSERT INTO `czb_file` VALUES ('155833815249388', 'abc.docx', '155583812434620', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155833814230148abc.docx?Expires=4711938145&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=zBma5JJ9T3YhgCkO75HqO65sZ88%3D', null, '2019-05-20 15:42:32', '2019-05-20 15:42:32');
INSERT INTO `czb_file` VALUES ('155840873587499', '50782598_p0.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/15584087282681250782598_p0.png?Expires=4712008735&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=mys5Z4CQVo74BJo0bQHFKzI7Tpo%3D', null, '2019-05-21 11:18:56', '2019-05-21 11:18:56');
INSERT INTO `czb_file` VALUES ('155840892381437', 'QQ图片20181115161319.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155840892102080QQ%E5%9B%BE%E7%89%8720181115161319.png?Expires=4712008923&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=b4y8u6DPKpCg7Qucq3RN6iP/KJA%3D', null, '2019-05-21 11:22:04', '2019-05-21 11:22:04');
INSERT INTO `czb_file` VALUES ('155844447964345', 'buy_card_with_ticket.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155844447854655buy_card_with_ticket.png?Expires=4712044479&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=X3zDpDCOcBtTceta9FFQII0i/d4%3D', null, '2019-05-21 21:14:40', '2019-05-21 21:14:40');
INSERT INTO `czb_file` VALUES ('155851495178635', '矩形 11@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155869161483277%E7%9F%A9%E5%BD%A2%2011%402x.png?Expires=4712291615&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=w0eV6fxEp24%2BkkSYWl4rxa5zElI%3D', null, '2019-05-24 17:53:35', '2019-05-24 17:53:35');
INSERT INTO `czb_file` VALUES ('155851502348275', '矩形 11@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155869158854687%E7%9F%A9%E5%BD%A2%2011%402x.png?Expires=4712291589&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=24cgS8b6QuJioTZkv%2BBV9/HIiRM%3D', null, '2019-05-24 17:53:09', '2019-05-24 17:53:09');
INSERT INTO `czb_file` VALUES ('155860161335453', '矩形 11@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155869733668375%E7%9F%A9%E5%BD%A2%2011%402x.png?Expires=4712297337&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=fJN8ROYPrJ/ygqUl%2B4tAbOblV/g%3D', null, '2019-05-24 19:28:57', '2019-05-24 19:28:57');
INSERT INTO `czb_file` VALUES ('155860165598368', '矩形 11@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155869795873505%E7%9F%A9%E5%BD%A2%2011%402x.png?Expires=4712297959&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=0ewL3Nz8N36YOw7S13wLM7Wq%2BUg%3D', null, '2019-05-24 19:39:19', '2019-05-24 19:39:19');
INSERT INTO `czb_file` VALUES ('155860169635705', '69e2504f78f0f7365156ae060455b319eac41390.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/15586016905073169e2504f78f0f7365156ae060455b319eac41390.jpg?Expires=4712201696&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=PCOHHwWM1BpI12CDl8vH4Ct3%2BZc%3D', null, '2019-05-23 16:54:56', '2019-05-23 16:54:56');
INSERT INTO `czb_file` VALUES ('155860220900192', '猎豹截图20180723214919.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155860220833193%E7%8C%8E%E8%B1%B9%E6%88%AA%E5%9B%BE20180723214919.png?Expires=4712202208&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=PVC4fWupJX5IY9UkMN7W9rX0ge4%3D', null, '2019-05-23 17:03:29', '2019-05-23 17:03:29');
INSERT INTO `czb_file` VALUES ('155860255416105', '5f90aefaaf51f3de2c835b639aeef01f3a29790f.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1558602551024655f90aefaaf51f3de2c835b639aeef01f3a29790f.jpg?Expires=4712202554&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=0fXh8N/aXwFHzdVpz4GrrjR8M4g%3D', null, '2019-05-23 17:09:14', '2019-05-23 17:09:14');
INSERT INTO `czb_file` VALUES ('155860367519589', '5555.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1558603666728235555.jpg?Expires=4712203675&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=psGG9EKhVo0PHumcdbuGs2NMZ4E%3D', null, '2019-05-23 17:27:55', '2019-05-23 17:27:55');
INSERT INTO `czb_file` VALUES ('155860744144504', '矩形 11@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155869757378277%E7%9F%A9%E5%BD%A2%2011%402x.png?Expires=4712297574&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=C7neLaQraShu2t90%2BN%2BT9qae78A%3D', null, '2019-05-24 19:32:54', '2019-05-24 19:32:54');
INSERT INTO `czb_file` VALUES ('155868556010351', '1.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/1558685559829671.jpg?Expires=4712285560&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=aS%2BH99mfz5t%2BGtJIqa4Udb4FG3Q%3D', null, '2019-05-24 16:12:40', '2019-05-24 16:12:40');
INSERT INTO `czb_file` VALUES ('155868717942979', '71.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/15586871778618871.jpg?Expires=4712287179&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=RwYzndGHOr0pIg5oospeavCanhg%3D', null, '2019-05-24 16:39:39', '2019-05-24 16:39:39');
INSERT INTO `czb_file` VALUES ('155868731599831', '71.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/15586873146801171.jpg?Expires=4712287315&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=t1%2BmfrFPbdmOBbhBRh%2Buyw/1HfE%3D', null, '2019-05-24 16:41:56', '2019-05-24 16:41:56');
INSERT INTO `czb_file` VALUES ('155879023472372', '形状 9@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155879023441380%E5%BD%A2%E7%8A%B6%209%402x.png?Expires=4712390234&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=aOjGMnLzDlGbAYWBuv4W6otIe1I%3D', null, '2019-05-25 21:17:15', '2019-05-25 21:17:15');
INSERT INTO `czb_file` VALUES ('155879031761271', '形状 9@2x.png', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155879031735056%E5%BD%A2%E7%8A%B6%209%402x.png?Expires=4712390317&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=9RluRqINVTfz4ZA/V1fU0EB62cE%3D', null, '2019-05-25 21:18:38', '2019-05-25 21:18:38');
INSERT INTO `czb_file` VALUES ('155879259810885', '357699.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155879259778685357699.jpg?Expires=4712392598&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=U15BxUxQGyNe6H6eWtVJbe%2BHTxQ%3D', null, '2019-05-25 21:56:38', '2019-05-25 21:56:38');
INSERT INTO `czb_file` VALUES ('155879410378203', '357699.jpg', '123456', 'http://czb-file.oss-cn-zhangjiakou.aliyuncs.com/155879410341036357699.jpg?Expires=4712394103&OSSAccessKeyId=LTAIjqFIGExMo9vx&Signature=GPSESVSK5TiuU4e0wv94jnicM6A%3D', null, '2019-05-25 22:21:44', '2019-05-25 22:21:44');
INSERT INTO `czb_file` VALUES ('2', 'nihao', '3', 'http://www.paochefang.com/wp-content/uploads/paoimage/2013/07/014635JO2.jpg', 'wqe', '2019-04-17 15:03:40', '2019-05-08 01:05:56');
INSERT INTO `czb_file` VALUES ('3', 'qwe', '1', 'https://www.jyacht.com/upload12xc/month_1310/201310211314556048.jpg', 'asf', '2019-05-29 15:03:53', '2019-05-29 15:03:56');

-- ----------------------------
-- Table structure for czb_income_log
-- ----------------------------
DROP TABLE IF EXISTS `czb_income_log`;
CREATE TABLE `czb_income_log` (
  `record_id` varchar(20) NOT NULL,
  `amount` double(10,2) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `info_id` varchar(20) DEFAULT NULL,
  `before_change_income` double(10,2) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `souse_id` varchar(64) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `withdraw_to` varchar(50) DEFAULT NULL,
  `withdraw_name` varchar(50) DEFAULT NULL,
  `withdraw_account` double(10,2) DEFAULT NULL,
  `commission_source_user` varchar(20) DEFAULT NULL,
  `commission_got_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_income_log
-- ----------------------------

-- ----------------------------
-- Table structure for czb_menu
-- ----------------------------
DROP TABLE IF EXISTS `czb_menu`;
CREATE TABLE `czb_menu` (
  `menu_id` varchar(20) NOT NULL,
  `menu_name` varchar(20) DEFAULT NULL,
  `menu_path` varchar(100) DEFAULT NULL,
  `menu_level` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `parent` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_menu
-- ----------------------------
INSERT INTO `czb_menu` VALUES ('1', '权限管理', '/admin/authManage', null, '2019-04-30 16:47:16', '2019-04-30 16:50:51', '155661422848449');
INSERT INTO `czb_menu` VALUES ('155627060335358', '角色管理', '/admin/roleManage', null, '2019-04-26 17:23:23', '2019-04-30 16:50:47', '155661422848449');
INSERT INTO `czb_menu` VALUES ('155627216533166', '用户管理', '/admin/userManage', null, '2019-04-26 17:49:25', '2019-04-30 16:51:10', '155661422848449');
INSERT INTO `czb_menu` VALUES ('155661422848449', '基础功能', '', null, '2019-04-30 16:50:28', '2019-05-07 17:06:14', '');
INSERT INTO `czb_menu` VALUES ('155661423572993', '系统功能', '', null, '2019-04-30 16:50:35', '2019-05-29 17:06:19', '');
INSERT INTO `czb_menu` VALUES ('155661523561527', 'app菜单管理', '/admin/appMenuManage', null, '2019-04-30 17:07:15', '2019-05-08 17:06:22', '155661422848449');
INSERT INTO `czb_menu` VALUES ('155662454074739', '企业管理', '/admin/enterpriseManage', null, '2019-04-30 19:42:20', '2019-05-07 17:06:28', '155661423572993');
INSERT INTO `czb_menu` VALUES ('155662455190444', '寄送管理', '/admin/courierManage', null, '2019-04-30 19:42:31', '2019-05-07 17:06:30', '155661423572993');
INSERT INTO `czb_menu` VALUES ('155662468607499', '油卡管理', '/admin/petrolManagement', null, '2019-04-30 19:44:46', '2019-05-07 17:06:33', '155661423572993');
INSERT INTO `czb_menu` VALUES ('155662470252679', '油卡销售管理', '/admin/petrolSellManagement', null, '2019-04-30 19:45:02', '2019-05-07 17:06:36', '155661423572993');
INSERT INTO `czb_menu` VALUES ('155678877845550', 'app公告管理', '/admin/appAnnouncementManage', null, '2019-05-02 17:19:40', '2019-05-07 17:06:45', '155661422848449');
INSERT INTO `czb_menu` VALUES ('155730086781839', '合同模板配置', '/admin/contractTemplateManagement', null, '2019-05-08 15:34:55', null, '155661423572993');
INSERT INTO `czb_menu` VALUES ('155730088560482', '合同套餐管理', '/admin/contractPackageManagement', null, '2019-05-08 15:35:13', null, '155661423572993');
INSERT INTO `czb_menu` VALUES ('155730090039740', '地区油类设置', '/admin/petrolAreaSetManagement', null, '2019-05-08 15:35:28', null, '155661423572993');
INSERT INTO `czb_menu` VALUES ('155790794107443', '充值管理', '/admin/recharge', null, '2019-05-15 16:12:22', '2019-05-23 01:13:51', '155661423572993');
INSERT INTO `czb_menu` VALUES ('155807577837274', '企业合同管理', '/admin/enterpriseContractManagement', null, '2019-05-17 14:49:40', null, '155661423572993');
INSERT INTO `czb_menu` VALUES ('155808364589602', '字典管理', '/admin/dicManagement', null, '2019-05-17 17:00:46', '2019-05-25 16:40:08', '155661422848449');
INSERT INTO `czb_menu` VALUES ('155823300672593', '打款记录', '/admin/payMoneyManage', null, '2019-05-19 10:30:06', null, '155661423572993');
INSERT INTO `czb_menu` VALUES ('155826877639625', '个人合同管理', '/admin/personalContractManagement', null, '2019-05-19 20:26:18', null, '155661423572993');
INSERT INTO `czb_menu` VALUES ('155833967059743', '收款记录', '/admin/receivedMoneyManage', null, '2019-05-20 16:07:50', null, '155661423572993');
INSERT INTO `czb_menu` VALUES ('155835437572978', '企业咨询管理', '/admin/consultingManage', null, '2019-05-20 20:12:55', null, '155661423572993');

-- ----------------------------
-- Table structure for czb_pay_to_person
-- ----------------------------
DROP TABLE IF EXISTS `czb_pay_to_person`;
CREATE TABLE `czb_pay_to_person` (
  `record_id` varchar(20) CHARACTER SET utf8 NOT NULL,
  `payee_name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `payee_id_card` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `contract_record_id` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '企业合同记录id',
  `payable_money` double(10,2) DEFAULT NULL,
  `actual_pay_money` double(10,2) DEFAULT NULL,
  `target_year_month` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `platform_pay_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '平台打款给个人的日期',
  `state` int(1) DEFAULT NULL COMMENT '0 未打款 1 已打款 2 打款异常',
  `is_deleted` int(1) unsigned zerofill DEFAULT NULL COMMENT '0 未删除 1 已删除',
  `remark` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_pay_to_person
-- ----------------------------

-- ----------------------------
-- Table structure for czb_petrol
-- ----------------------------
DROP TABLE IF EXISTS `czb_petrol`;
CREATE TABLE `czb_petrol` (
  `petrol_id` varchar(20) NOT NULL,
  `petrol_num` varchar(20) DEFAULT NULL,
  `petrol_psw` varchar(20) DEFAULT NULL,
  `petrol_kind` int(1) DEFAULT NULL,
  `petrol_denomination` double(10,0) DEFAULT NULL,
  `petrol_price` double(10,2) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `discount` double(10,0) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `owner_id` varchar(20) DEFAULT NULL,
  `petrol_type` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `commission` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`petrol_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_petrol
-- ----------------------------

-- ----------------------------
-- Table structure for czb_petrol_delivery_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_petrol_delivery_records`;
CREATE TABLE `czb_petrol_delivery_records` (
  `record_id` varchar(20) NOT NULL,
  `petrol_num` varchar(20) DEFAULT NULL,
  `address_id` varchar(20) DEFAULT NULL,
  `delivery_state` int(1) DEFAULT NULL COMMENT '0 未邮寄，1 邮寄中，2 已收货',
  `delivery_num` varchar(50) DEFAULT NULL,
  `delivery_company` varchar(50) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_petrol_delivery_records
-- ----------------------------

-- ----------------------------
-- Table structure for czb_petrol_price_report
-- ----------------------------
DROP TABLE IF EXISTS `czb_petrol_price_report`;
CREATE TABLE `czb_petrol_price_report` (
  `petrol_price_report_id` varchar(20) NOT NULL,
  `petrol_name` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `petrol_price` double(10,2) DEFAULT NULL,
  `area` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`petrol_price_report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of czb_petrol_price_report
-- ----------------------------

-- ----------------------------
-- Table structure for czb_petrol_sales_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_petrol_sales_records`;
CREATE TABLE `czb_petrol_sales_records` (
  `record_id` varchar(20) NOT NULL,
  `petrol_num` varchar(20) DEFAULT NULL,
  `buyer_id` varchar(20) DEFAULT NULL,
  `payment_method` int(1) DEFAULT NULL,
  `third_order_id` varchar(80) DEFAULT NULL,
  `turnover_amount` double(10,2) DEFAULT NULL,
  `transaction_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `petrol_id` varchar(20) DEFAULT NULL,
  `state` int(255) DEFAULT NULL,
  `contract_id` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `record_type` int(1) DEFAULT NULL,
  `is_recharged` int(1) DEFAULT NULL,
  `petrol_kind` int(1) DEFAULT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_petrol_sales_records
-- ----------------------------

-- ----------------------------
-- Table structure for czb_petrol_sale_config
-- ----------------------------
DROP TABLE IF EXISTS `czb_petrol_sale_config`;
CREATE TABLE `czb_petrol_sale_config` (
  `petrol_config_id` varchar(20) NOT NULL,
  `petrol_type` int(1) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `sale_state` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`petrol_config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_petrol_sale_config
-- ----------------------------
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767681', '0', '河北省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767682', '1', '河北省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767683', '0', '山西省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767684', '1', '山西省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767685', '0', '内蒙古自治区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767686', '1', '内蒙古自治区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767687', '0', '辽宁省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767688', '1', '辽宁省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767689', '0', '吉林省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767690', '1', '吉林省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767691', '0', '黑龙江省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767692', '1', '黑龙江省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767693', '0', '江苏省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767694', '1', '江苏省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767695', '0', '浙江省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767696', '1', '浙江省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767697', '0', '安徽省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767698', '1', '安徽省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767699', '0', '福建省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767700', '1', '福建省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767701', '0', '江西省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767702', '1', '江西省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767703', '0', '山东省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767704', '1', '山东省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767705', '0', '河南省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767706', '1', '河南省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767707', '0', '湖北省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767708', '1', '湖北省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767709', '0', '广东省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767710', '1', '广东省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767711', '0', '广西壮族自治区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767712', '1', '广西壮族自治区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767713', '0', '海南省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767714', '1', '海南省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767715', '0', '四川省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767716', '1', '四川省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767717', '0', '贵州省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767718', '1', '贵州省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767719', '0', '云南省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767720', '1', '云南省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767721', '0', '陕西省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767722', '1', '陕西省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767723', '0', '甘肃省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767724', '1', '甘肃省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767725', '0', '青海省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767726', '1', '青海省', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767727', '0', '西藏自治区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767728', '1', '西藏自治区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767729', '0', '宁夏回族自治区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767730', '1', '宁夏回族自治区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767731', '0', '新疆维吾尔自治区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767732', '1', '新疆维吾尔自治区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767733', '0', '香港特别行政区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767734', '1', '香港特别行政区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767735', '0', '澳门特别行政区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767736', '1', '澳门特别行政区', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767737', '0', '北京市', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767738', '1', '北京市', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767739', '0', '天津市', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767740', '1', '天津市', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767741', '0', '上海市', '0', '2019-05-20 16:37:40', '2019-05-20 16:37:40');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767742', '1', '上海市', '0', '2019-05-20 16:37:40', '2019-05-21 16:44:58');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767743', '0', '重庆市', '0', '2019-05-20 16:37:40', '2019-05-24 23:31:45');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767744', '1', '重庆市', '0', '2019-05-20 16:37:40', '2019-05-24 23:31:45');
INSERT INTO `czb_petrol_sale_config` VALUES ('26144626863767745', '2', '重庆市', '0', '2019-05-20 16:37:40', '2019-05-24 23:31:45');

-- ----------------------------
-- Table structure for czb_platform_income_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_platform_income_records`;
CREATE TABLE `czb_platform_income_records` (
  `record_id` varchar(20) CHARACTER SET utf8 NOT NULL,
  `contract_record_id` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '企业合同记录id',
  `receivable_money` double(10,2) DEFAULT NULL,
  `actual_receipts_money` double(10,2) DEFAULT NULL,
  `target_year_month` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `enterprise_pay_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '企业向平台打款日期',
  `state` int(1) DEFAULT NULL COMMENT '0 未收款 1 已收款 2收款异常',
  `is_deleted` int(1) DEFAULT NULL COMMENT '0 未删除 1 已删除',
  `is_need_recharge` int(1) DEFAULT NULL,
  `is_distributed` int(1) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_platform_income_records
-- ----------------------------

-- ----------------------------
-- Table structure for czb_role
-- ----------------------------
DROP TABLE IF EXISTS `czb_role`;
CREATE TABLE `czb_role` (
  `role_id` varchar(20) NOT NULL,
  `role_name` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_role
-- ----------------------------
INSERT INTO `czb_role` VALUES ('155627218194458', '管理员', '2019-04-26 17:49:42', '2019-04-26 17:55:16');
INSERT INTO `czb_role` VALUES ('155627256682623', '超级管理员', '2019-04-26 17:56:06', null);

-- ----------------------------
-- Table structure for czb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `czb_role_menu`;
CREATE TABLE `czb_role_menu` (
  `id` varchar(20) NOT NULL,
  `role_id` varchar(20) DEFAULT NULL,
  `menu_id` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_role_menu
-- ----------------------------
INSERT INTO `czb_role_menu` VALUES ('155835587217773', '155627218194458', '155627060335358', '2019-05-20 20:37:52', null);
INSERT INTO `czb_role_menu` VALUES ('155835587941311', '155627256682623', '155661423572993', '2019-05-20 20:37:59', null);
INSERT INTO `czb_role_menu` VALUES ('155835587941339', '155627256682623', '155627060335358', '2019-05-20 20:37:59', null);
INSERT INTO `czb_role_menu` VALUES ('155835587941342', '155627256682623', '155627216533166', '2019-05-20 20:37:59', null);
INSERT INTO `czb_role_menu` VALUES ('155835587941392', '155627256682623', '155661422848449', '2019-05-20 20:37:59', null);
INSERT INTO `czb_role_menu` VALUES ('155835587941398', '155627256682623', '1', '2019-05-20 20:37:59', null);
INSERT INTO `czb_role_menu` VALUES ('155835589493207', '155627256682623', '155662454074739', '2019-05-20 20:38:15', null);
INSERT INTO `czb_role_menu` VALUES ('155835589493234', '155627256682623', '155661523561527', '2019-05-20 20:38:15', null);
INSERT INTO `czb_role_menu` VALUES ('155835589493257', '155627256682623', '155662455190444', '2019-05-20 20:38:15', null);
INSERT INTO `czb_role_menu` VALUES ('155835589493288', '155627256682623', '155662468607499', '2019-05-20 20:38:15', null);
INSERT INTO `czb_role_menu` VALUES ('155835592845307', '155627256682623', '155730086781839', '2019-05-20 20:38:48', null);
INSERT INTO `czb_role_menu` VALUES ('155835592845311', '155627256682623', '155730088560482', '2019-05-20 20:38:48', null);
INSERT INTO `czb_role_menu` VALUES ('155835592845338', '155627256682623', '155662470252679', '2019-05-20 20:38:48', null);
INSERT INTO `czb_role_menu` VALUES ('155835592845381', '155627256682623', '155730090039740', '2019-05-20 20:38:48', null);
INSERT INTO `czb_role_menu` VALUES ('155835592845399', '155627256682623', '155678877845550', '2019-05-20 20:38:48', null);
INSERT INTO `czb_role_menu` VALUES ('155835593833153', '155627256682623', '155823300672593', '2019-05-20 20:38:58', null);
INSERT INTO `czb_role_menu` VALUES ('155835593833163', '155627256682623', '155808364589602', '2019-05-20 20:38:58', null);
INSERT INTO `czb_role_menu` VALUES ('155835593833177', '155627256682623', '155807577837274', '2019-05-20 20:38:58', null);
INSERT INTO `czb_role_menu` VALUES ('155835593833192', '155627256682623', '155790794107443', '2019-05-20 20:38:58', null);
INSERT INTO `czb_role_menu` VALUES ('155835603479383', '155627256682623', '155835437572978', '2019-05-20 20:40:35', null);
INSERT INTO `czb_role_menu` VALUES ('155835603955540', '155627256682623', '155833967059743', '2019-05-20 20:40:39', null);
INSERT INTO `czb_role_menu` VALUES ('155835603955573', '155627256682623', '155826877639625', '2019-05-20 20:40:39', null);
INSERT INTO `czb_role_menu` VALUES ('155840672672779', '155627218194458', '1', '2019-05-21 10:45:27', null);
INSERT INTO `czb_role_menu` VALUES ('155879365803879', '155627218194458', '155833967059743', '2019-05-25 22:14:18', null);
INSERT INTO `czb_role_menu` VALUES ('155879368448642', '155627218194458', '155661422848449', '2019-05-25 22:14:45', null);
INSERT INTO `czb_role_menu` VALUES ('155879368448691', '155627218194458', '155661423572993', '2019-05-25 22:14:45', null);
INSERT INTO `czb_role_menu` VALUES ('155879376592414', '155627218194458', '155662454074739', '2019-05-25 22:16:09', null);
INSERT INTO `czb_role_menu` VALUES ('155879376592435', '155627218194458', '155661523561527', '2019-05-25 22:16:09', null);
INSERT INTO `czb_role_menu` VALUES ('155879376592462', '155627218194458', '155627216533166', '2019-05-25 22:16:09', null);
INSERT INTO `czb_role_menu` VALUES ('155879377187301', '155627218194458', '155662468607499', '2019-05-25 22:16:15', null);
INSERT INTO `czb_role_menu` VALUES ('155879377187366', '155627218194458', '155662455190444', '2019-05-25 22:16:15', null);
INSERT INTO `czb_role_menu` VALUES ('155879377187383', '155627218194458', '155662470252679', '2019-05-25 22:16:15', null);
INSERT INTO `czb_role_menu` VALUES ('155879377821308', '155627218194458', '155678877845550', '2019-05-25 22:16:22', null);
INSERT INTO `czb_role_menu` VALUES ('155879377821318', '155627218194458', '155730090039740', '2019-05-25 22:16:22', null);
INSERT INTO `czb_role_menu` VALUES ('155879377821346', '155627218194458', '155730086781839', '2019-05-25 22:16:22', null);
INSERT INTO `czb_role_menu` VALUES ('155879377821360', '155627218194458', '155730088560482', '2019-05-25 22:16:22', null);
INSERT INTO `czb_role_menu` VALUES ('155879378311806', '155627218194458', '155807577837274', '2019-05-25 22:16:27', null);
INSERT INTO `czb_role_menu` VALUES ('155879378311821', '155627218194458', '155808364589602', '2019-05-25 22:16:27', null);
INSERT INTO `czb_role_menu` VALUES ('155879378311826', '155627218194458', '155823300672593', '2019-05-25 22:16:27', null);
INSERT INTO `czb_role_menu` VALUES ('155879378311875', '155627218194458', '155790794107443', '2019-05-25 22:16:27', null);
INSERT INTO `czb_role_menu` VALUES ('155879378738626', '155627218194458', '155826877639625', '2019-05-25 22:16:31', null);
INSERT INTO `czb_role_menu` VALUES ('155879378738637', '155627218194458', '155835437572978', '2019-05-25 22:16:31', null);

-- ----------------------------
-- Table structure for czb_service_plan
-- ----------------------------
DROP TABLE IF EXISTS `czb_service_plan`;
CREATE TABLE `czb_service_plan` (
  `plan_id` varchar(20) NOT NULL,
  `plan_name` varchar(20) DEFAULT NULL,
  `plan_amount` double(10,0) DEFAULT NULL,
  `plan_time` int(2) DEFAULT NULL,
  `rent_back_money` double(10,0) DEFAULT NULL,
  `pay_money` double(10,0) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`plan_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_service_plan
-- ----------------------------

-- ----------------------------
-- Table structure for czb_user
-- ----------------------------
DROP TABLE IF EXISTS `czb_user`;
CREATE TABLE `czb_user` (
  `user_id` varchar(20) NOT NULL,
  `user_account` varchar(20) DEFAULT NULL,
  `user_psw` varchar(100) DEFAULT NULL,
  `user_type` int(1) DEFAULT NULL,
  `user_rank` int(1) DEFAULT NULL,
  `is_identified` int(1) DEFAULT NULL,
  `superior_user` varchar(20) DEFAULT NULL,
  `is_login_pc` int(1) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_id_card` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `user_yun_id` varchar(255) DEFAULT NULL,
  `is_deleted` int(1) DEFAULT NULL,
  `commission_level_one` varchar(20) DEFAULT NULL,
  `commission_level_two` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_user
-- ----------------------------
INSERT INTO `czb_user` VALUES ('155583812434620', '123456', '$2a$10$obdBVkq.WvymLOMAuoygG.dOaHMHF247l5A/kk79HbmqUSzLnMrJW', '1', '0', '0', '155779902355425', '1', '123456', null, null, '2019-05-25 23:33:49', null, '0', null, null);

-- ----------------------------
-- Table structure for czb_user_income_info
-- ----------------------------
DROP TABLE IF EXISTS `czb_user_income_info`;
CREATE TABLE `czb_user_income_info` (
  `info_id` varchar(20) NOT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `fanyong_income` double(10,2) DEFAULT NULL,
  `share_income` double(10,2) DEFAULT NULL,
  `pay_total_rent` double(10,2) DEFAULT NULL,
  `got_total_rent` double(10,2) DEFAULT NULL,
  `other_income` double(10,2) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `withdrawed` double(10,2) DEFAULT '0.00',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_user_income_info
-- ----------------------------

-- ----------------------------
-- Table structure for czb_user_login_info
-- ----------------------------
DROP TABLE IF EXISTS `czb_user_login_info`;
CREATE TABLE `czb_user_login_info` (
  `record_id` varchar(20) NOT NULL,
  `login_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `device_type` varchar(30) DEFAULT NULL,
  `system_version` varchar(30) DEFAULT NULL,
  `longitude` varchar(30) DEFAULT NULL,
  `latitude` varchar(30) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_user_login_info
-- ----------------------------

-- ----------------------------
-- Table structure for czb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `czb_user_role`;
CREATE TABLE `czb_user_role` (
  `id` varchar(20) NOT NULL,
  `role_id` varchar(20) DEFAULT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_user_role
-- ----------------------------
INSERT INTO `czb_user_role` VALUES ('1', '155627218194458', '155583812434620', '2019-05-25 22:12:53', '2019-05-25 22:12:59');

-- ----------------------------
-- Table structure for czb_verification_code
-- ----------------------------
DROP TABLE IF EXISTS `czb_verification_code`;
CREATE TABLE `czb_verification_code` (
  `verification_code_id` char(20) NOT NULL,
  `user_account` char(20) DEFAULT NULL,
  `content` char(250) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`verification_code_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_verification_code
-- ----------------------------

-- ----------------------------
-- Table structure for czb_vip_recharge_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_vip_recharge_records`;
CREATE TABLE `czb_vip_recharge_records` (
  `record_id` varchar(20) NOT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `account` double(10,2) DEFAULT NULL,
  `recharge_way` int(1) DEFAULT NULL,
  `is_received` int(1) DEFAULT NULL,
  `third_trade_num` varchar(50) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of czb_vip_recharge_records
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;