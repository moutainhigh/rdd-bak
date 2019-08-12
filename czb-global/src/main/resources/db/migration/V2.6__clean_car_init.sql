/*==============================================================*/
/* DBMS name:      MySQL 8.0                                    */
/* Created on:     8/12/2019 11:02:40 AM                        */
/*==============================================================*/


drop table if exists czb_clean_rider;

drop table if exists czb_clean_server_vehicle;

drop table if exists czb_compared_pic;

drop table if exists czb_coupon_standard;

drop table if exists czb_server_coupon;

drop table if exists czb_server_standard;

drop table if exists czb_vehicle_clean_order;

/*==============================================================*/
/* Table: czb_clean_rider                                       */
/*==============================================================*/
create table czb_clean_rider
(
   rider_id             varchar(20) not null,
   rider_name           varchar(12),
   contact_number       varchar(11),
   status               tinyint,
   user_id              varchar(20),
   create_at timestamp NULL DEFAULT NULL,
   update_at timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   primary key (rider_id)
);

/*==============================================================*/
/* Table: czb_clean_server_vehicle                              */
/*==============================================================*/
create table czb_clean_server_vehicle
(
   vehicle_id           varchar(20) not null,
   user_id              varchar(20),
   user_name            varchar(20),
   license_number       varchar(20),
   vehicle_color        varchar(10),
   vehicle_type         tinyint,
   vehicle_series       varchar(15),
   create_at timestamp NULL DEFAULT NULL,
   update_at timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   primary key (vehicle_id)
);

/*==============================================================*/
/* Table: czb_compared_pic                                      */
/*==============================================================*/
create table czb_compared_pic
(
   compared_pic_id      varchar(20) not null,
   server_order_id      varchar(20),
   status               tinyint,
   file_id              varchar(20),
   create_at timestamp NULL DEFAULT NULL,
   update_at timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   primary key (compared_pic_id)
);

/*==============================================================*/
/* Table: czb_coupon_standard                                   */
/*==============================================================*/
create table czb_coupon_standard
(
   standard_id          varchar(20) not null,
   standard_type        varchar(10),
   standard_value       double(10,2),
   continue_days        int,
   standard_explain     varchar(60),
   create_at timestamp NULL DEFAULT NULL,
   update_at timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   primary key (standard_id)
);

/*==============================================================*/
/* Table: czb_server_coupon                                     */
/*==============================================================*/
create table czb_server_coupon
(
   coupon_id            varchar(20) not null,
   destroy_time         timestamp NULL DEFAULT NULL,
   coupon_standard      varchar(20),
   owner_id             varchar(20),
   status               tinyint,
   create_at timestamp NULL DEFAULT NULL,
   update_at timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   primary key (coupon_id)
);

/*==============================================================*/
/* Table: czb_server_standard                                   */
/*==============================================================*/
create table czb_server_standard
(
   server_id            varchar(20) not null,
   server_name          varchar(20),
   server_type          varchar(2),
   server_price         double(10,2),
   server_explain       varchar(60),
   server_discount      float(4),
   server_vehicle_type  tinyint,
   create_at timestamp NULL DEFAULT NULL,
   update_at timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   primary key (server_id)
);

/*==============================================================*/
/* Table: czb_vehicle_clean_order                               */
/*==============================================================*/
create table czb_vehicle_clean_order
(
   server_order_id      varchar(20) not null,
   user_id              varchar(20),
   rider_id             varchar(20),
   third_order          varchar(50),
   pay_status           tinyint,
   actual_price         double(10,2),
   vehicle_id           varchar(20),
   process_status       tinyint,
   cancel_time          timestamp NULL DEFAULT NULL,
   cancel_person_id     varchar(20),
   cancel_reason        varchar(60),
   create_at timestamp NULL DEFAULT NULL,
   update_at timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
   primary key (server_order_id)
);

