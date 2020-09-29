package com.cqut.czb.bn.entity.dto.appBuyPetrol;

public class PetrolInputDTO {

    private String remark;

    private String ownerId;

    private Integer petrolKind;

    private Double petrolPrice;

    private String userAccount;

    private String addressId;

    private String PaymentOrder;

    private String contractId;//合同订单

    private String payType;//执行的操作0为购油。2为充值

    private Integer paymentMethod;//0 佣金购买，1 支付宝，2 微信，3 自己开发的方案，4 合同打款

    private String area;

    private Integer isVip;

    private Integer isHaveVip;

    private Integer isSpecial;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsHaveVip() {
        return isHaveVip;
    }

    public void setIsHaveVip(Integer isHaveVip) {
        this.isHaveVip = isHaveVip;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public PetrolInputDTO() {
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getPaymentOrder() {
        return PaymentOrder;
    }

    public void setPaymentOrder(String paymentOrder) {
        PaymentOrder = paymentOrder;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
        this.petrolKind = petrolKind;
    }

    public Double getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(Double petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }
}
