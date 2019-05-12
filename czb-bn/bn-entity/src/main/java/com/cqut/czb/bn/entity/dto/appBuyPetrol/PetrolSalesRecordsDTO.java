package com.cqut.czb.bn.entity.dto.appBuyPetrol;

import com.alipay.api.domain.AlipayTradeAppPayModel;

import com.cqut.czb.bn.entity.dto.paymentRecord.AiHuAlipayConfig;
import com.cqut.czb.bn.util.string.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PetrolSalesRecordsDTO {
    private Double actualPayment;//实际支付金额

    private int petrolKind;

    private String recordId;

    private String petrolNum;

    private String buyerId;

    private Integer paymentMethod;//支付方式

    private Integer payType;//支付种类（0代表充值，1代表购油）

    private String thirdOrderId;

    private Double turnoverAmount;//成交金额

    private Date transactionTime;

    private String petrolId;

    private Integer state;

    private String contractId;

    private Date createAt;

    private Date updateAt;

    private String remark; // 备注

    private double petrolPrice;//油卡价格

    private int count;//油卡数量

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(double petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public Double getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(Double actualPayment) {
        this.actualPayment = actualPayment;
    }

    public int getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(int petrolKind) {
        this.petrolKind = petrolKind;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getThirdOrderId() {
        return thirdOrderId;
    }

    public void setThirdOrderId(String thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public Double getTurnoverAmount() {
        return turnoverAmount;
    }

    public void setTurnoverAmount(Double turnoverAmount) {
        this.turnoverAmount = turnoverAmount;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getPetrolId() {
        return petrolId;
    }

    public void setPetrolId(String petrolId) {
        this.petrolId = petrolId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }



    public String getPassbackParams(String orgId, String payType,
                                    Double money, Integer count,
                                    Integer petrolKind ,String ownerId,
                                    String petrolNum,Double actualPayment,String addressId) {
        Map<String, Object> pbp = new HashMap<>();

        pbp.put("orgId", orgId);
        pbp.put("payType", payType);
        pbp.put("money", money);
        pbp.put("count", count);
        pbp.put("petrolKind", petrolKind);
        pbp.put("ownerId", ownerId);
        pbp.put("petrolNum", petrolNum);
        pbp.put("actualPayment", actualPayment);
        pbp.put("addressId", addressId);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体
     * @return
     */
    public AlipayTradeAppPayModel toAlipayTradeAppPayModel(String orgId, String payType,
                                                           double money, Integer count,
                                                           Integer petrolKind ,String ownerId,
                                                           String petrolNum,double actualPayment,String addressId) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(remark);
        model.setSubject("爱虎购油");
        model.setOutTradeNo(orgId);
        model.setTimeoutExpress(AiHuAlipayConfig.timeout_express);
        model.setTotalAmount("0.01");
        model.setProductCode(AiHuAlipayConfig.product_code);
        model.setPassbackParams(getPassbackParams(orgId, payType, money, count,petrolKind,ownerId,petrolNum,actualPayment,addressId));
        return model;
    }
}
