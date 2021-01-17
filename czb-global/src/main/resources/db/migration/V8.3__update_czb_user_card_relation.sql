ALTER TABLE czb_user_card_relation CHANGE petrol_id sinopec_petrol_num varchar(20) DEFAULT NULL;

ALTER TABLE czb_user_card_relation ADD COLUMN petrolChina_petrol_num varchar(20) DEFAULT NULL AFTER sinopec_petrol_num