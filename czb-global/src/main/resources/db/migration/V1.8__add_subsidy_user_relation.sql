-- ----------------------------
-- Table structure for czb_subsidy_user
-- ----------------------------
DROP TABLE IF EXISTS `czb_subsidy_user`;
CREATE TABLE `czb_subsidy_user` (
  `relation_id` varchar(20) NOT NULL,
  `mission_id` varchar(20) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `amount` double(10,2) NOT NULL,
  PRIMARY KEY (`relation_id`),
  KEY `user_id` (`user_id`),
  KEY `mission_id` (`mission_id`),
  CONSTRAINT `czb_subsidy_user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `czb_user` (`user_id`),
  CONSTRAINT `czb_subsidy_user_ibfk_2` FOREIGN KEY (`mission_id`) REFERENCES `czb_subsidy_mission` (`mission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;