package com.cqut.czb.bn.util.config.SendMesConfig;

public class MesInfo {

  public enum userId {
        BOSS("155888669469738"),

        TEST("155930101061936");

        private String userId;

        userId(String userId) {
            this.userId = userId;
        }

        public String getUserId() {
          return userId;
        }
  }

    public enum noticeId{
        REGISTER("589400087460259249"),BUY_PETROL("589478606469609864"),
        RECHARGE_VIP("589488759910421661"),RECHARGE_PETROL("589854661255911652"),
        WASH_CAR("688008757855812"),ORDER_START("8708831135559901"),
        RECHARGE_PETROL_USER("810862694366655853"), DELIVERY_PETROL_USER("4664831858926537"),
        RECHARGE_FAIL("123123123");
        private String noticeId;

        noticeId(String noticeId) {
            this.noticeId = noticeId;
        }

        public String getNoticeId() {
            return noticeId;
        }
    }
}
