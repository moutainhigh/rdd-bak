/*
Navicat MySQL Data Transfer

Source Server         : 39.98.250.45_3306
Source Server Version : 50725
Source Host           : 39.98.250.45:3306
Source Database       : czb_db

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-04-20 20:01:29
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
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`announcement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_announcement
-- ----------------------------
INSERT INTO `czb_announcement` VALUES ('1', '11', '11', '11', '11', '11', '11', '11', null, null);

-- ----------------------------
-- Table structure for czb_app_router
-- ----------------------------
DROP TABLE IF EXISTS `czb_app_router`;
CREATE TABLE `czb_app_router` (
  `router_id` varchar(20) NOT NULL,
  `app_type` int(1) DEFAULT NULL,
  `menu_name` varchar(20) DEFAULT NULL,
  `menu_path` varchar(50) DEFAULT NULL,
  `is_show` int(1) DEFAULT NULL,
  `icon_path_id` varchar(20) DEFAULT NULL,
  `menu_identity_code` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`router_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_app_router
-- ----------------------------

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
  PRIMARY KEY (`person_car_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_cars_persons
-- ----------------------------

-- ----------------------------
-- Table structure for czb_contract_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_contract_records`;
CREATE TABLE `czb_contract_records` (
  `record_id` varchar(20) DEFAULT NULL,
  `signed_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `user_id` varchar(20) DEFAULT NULL,
  `contract_start_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `contract_end_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `contract_state` int(1) DEFAULT NULL,
  `contract_type` int(1) DEFAULT NULL,
  `rent` double(10,0) DEFAULT NULL,
  `third_contract_num` varchar(20) DEFAULT NULL,
  `got_total_money` double(10,0) DEFAULT NULL,
  `judge_user_id` varchar(20) DEFAULT NULL,
  `judge_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `pay_total_money` double(10,0) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_contract_records
-- ----------------------------

-- ----------------------------
-- Table structure for czb_deposit_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_deposit_records`;
CREATE TABLE `czb_deposit_records` (
  `record_id` varchar(20) NOT NULL,
  `payer` varchar(20) DEFAULT NULL,
  `pay_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `amount` double(10,0) DEFAULT NULL,
  `is_recharged` int(1) DEFAULT NULL,
  `payer_id` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_deposit_records
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
  PRIMARY KEY (`enterprise_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_enterprise_info
-- ----------------------------

-- ----------------------------
-- Table structure for czb_file
-- ----------------------------
DROP TABLE IF EXISTS `czb_file`;
CREATE TABLE `czb_file` (
  `file_id` varchar(20) NOT NULL,
  `file_name` varchar(20) DEFAULT NULL,
  `uploader` varchar(20) DEFAULT NULL,
  `save_path` varchar(100) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_file
-- ----------------------------

-- ----------------------------
-- Table structure for czb_income_log
-- ----------------------------
DROP TABLE IF EXISTS `czb_income_log`;
CREATE TABLE `czb_income_log` (
  `record_id` varchar(20) NOT NULL,
  `amount` double(10,0) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `info_id` varchar(20) DEFAULT NULL,
  `before_change_income` double(10,0) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `souse_id` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `menu_path` varchar(20) DEFAULT NULL,
  `menu_level` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_menu
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
  `petrol_price` double(10,0) DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `discount` double(10,0) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `owner_id` varchar(20) DEFAULT NULL,
  `petrol_type` int(1) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`petrol_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_petrol
-- ----------------------------
INSERT INTO `czb_petrol` VALUES ('4654', '454', '545', '1', '21', '20', '1', '1', '重庆市', null, '1', '2019-04-19 20:53:58', '2019-04-19 20:54:01');

-- ----------------------------
-- Table structure for czb_petrol_sales_records
-- ----------------------------
DROP TABLE IF EXISTS `czb_petrol_sales_records`;
CREATE TABLE `czb_petrol_sales_records` (
  `record_id` varchar(20) NOT NULL,
  `petrol_num` varchar(20) DEFAULT NULL,
  `buyer_id` varchar(20) DEFAULT NULL,
  `payment_method` int(1) DEFAULT NULL,
  `third_order_id` varchar(20) DEFAULT NULL,
  `turnover_amount` double(10,0) DEFAULT NULL,
  `transaction_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `petrol_id` varchar(20) DEFAULT NULL,
  `state` int(255) DEFAULT NULL,
  `contract_id` varchar(20) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`petrol_config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_petrol_sale_config
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
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_role
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for czb_service_plan
-- ----------------------------
DROP TABLE IF EXISTS `czb_service_plan`;
CREATE TABLE `czb_service_plan` (
  `plan_id` varchar(20) NOT NULL,
  `plan_name` varchar(20) DEFAULT NULL,
  `plan_amoount` double(10,0) DEFAULT NULL,
  `plan_time` int(2) DEFAULT NULL,
  `rent_back_money` double(10,0) DEFAULT NULL,
  `pay_money` double(10,0) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_user
-- ----------------------------

-- ----------------------------
-- Table structure for czb_user_income_info
-- ----------------------------
DROP TABLE IF EXISTS `czb_user_income_info`;
CREATE TABLE `czb_user_income_info` (
  `info_id` varchar(20) NOT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `fanyong_income` double(10,0) DEFAULT NULL,
  `share_income` double(10,0) DEFAULT NULL,
  `pay_total_rent` double(10,0) DEFAULT NULL,
  `got_total_rent` double(10,0) DEFAULT NULL,
  `other_income` double(10,0) DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of czb_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for schema_version
-- ----------------------------
DROP TABLE IF EXISTS `schema_version`;
CREATE TABLE `schema_version` (
  `version_rank` int(11) NOT NULL,
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`version`),
  KEY `schema_version_vr_idx` (`version_rank`),
  KEY `schema_version_ir_idx` (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schema_version
-- ----------------------------
INSERT INTO `schema_version` VALUES ('1', '1', '1', '<< Flyway Baseline >>', 'BASELINE', '<< Flyway Baseline >>', null, 'root', '2019-04-19 10:37:23', '0', '1');
INSERT INTO `schema_version` VALUES ('2', '2', '1.1', 'init', 'SQL', 'V1.1__init.sql', '1564218184', 'root', '2019-04-19 10:37:25', '872', '1');
INSERT INTO `schema_version` VALUES ('3', '3', '1.2', 'init2', 'SQL', 'V1.2__init2.sql', '-2043651957', 'root', '2019-04-19 10:37:33', '7331', '1');
INSERT INTO `schema_version` VALUES ('4', '4', '1.3', 'modify', 'SQL', 'V1.3__modify.sql', '804185081', 'root', '2019-04-19 20:48:33', '7482', '1');
