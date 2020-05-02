package com.cqut.czb.bn.entity.dto.paymentCallBack;

public class AliPetrolCouponsDTO {

        private String orgId;

        private Integer payType;

        private Integer petrolKind;

        private String userId;

        private String petrolId;

        private String area;

        private String userAccount;

        private Double petrolPrice;

        private String thirdOrderId;

        public String getThirdOrderId() {
                return thirdOrderId;
        }

        public void setThirdOrderId(String thirdOrderId) {
                this.thirdOrderId = thirdOrderId;
        }

        public String getOrgId() {
                return orgId;
        }

        public void setOrgId(String orgId) {
                this.orgId = orgId;
        }

        public Integer getPayType() {
                return payType;
        }

        public void setPayType(Integer payType) {
                this.payType = payType;
        }

        public Integer getPetrolKind() {
                return petrolKind;
        }

        public void setPetrolKind(Integer petrolKind) {
                this.petrolKind = petrolKind;
        }

        public String getUserAccount() {
                return userAccount;
        }

        public void setUserAccount(String userAccount) {
                this.userAccount = userAccount;
        }

        public String getUserId() {
                return userId;
        }

        public void setUserId(String userId) {
                this.userId = userId;
        }

        public String getPetrolId() {
                return petrolId;
        }

        public void setPetrolId(String petrolId) {
                this.petrolId = petrolId;
        }

        public String getArea() {
                return area;
        }

        public void setArea(String area) {
                this.area = area;
        }

        public Double getPetrolPrice() {
                return petrolPrice;
        }

        public void setPetrolPrice(Double petrolPrice) {
                this.petrolPrice = petrolPrice;
        }
}
