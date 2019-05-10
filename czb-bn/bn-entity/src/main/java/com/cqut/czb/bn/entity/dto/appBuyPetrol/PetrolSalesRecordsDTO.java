package com.cqut.czb.bn.entity.dto.appBuyPetrol;

import com.alipay.api.domain.AlipayTradeAppPayModel;

import com.cqut.czb.bn.entity.dto.paymentRecord.AiHuAlipayConfig;
import com.cqut.czb.bn.util.string.StringUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PetrolSalesRecordsDTO {
    private Integer petrolKind;

    private String recordId;

    private String petrolNum;

    private String buyerId;

    private Integer paymentMethod;

    private String thirdOrderId;

    private Double turnoverAmount;//成交金额

    private Date transactionTime;

    private String petrolId;

    private Integer state;

    private String contractId;

    private Date createAt;

    private Date updateAt;

    // 备注
    private String remark;

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
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
                                    String petrolNum,Double actualPayment) {
        Map<String, Object> pbp = new HashMap<>();

        pbp.put("orgId", orgId);
        pbp.put("payType", payType);
        pbp.put("money", money);
        pbp.put("count", count);
        pbp.put("petrolKind", petrolKind);
        pbp.put("ownerId", ownerId);
        pbp.put("petrolNum", petrolNum);
        pbp.put("actualPayment", actualPayment);
        return StringUtil.transMapToStringOther(pbp);
    }

    /**
     * 转换为支付宝支付实体
     * @return
     */
    public AlipayTradeAppPayModel toAlipayTradeAppPayModel(String orgId, String payType,
                                                           double money, Integer count,
                                                           Integer petrolKind ,String ownerId,
                                                           String petrolNum,double actualPayment) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(remark);
        model.setSubject("爱虎购油");
        model.setOutTradeNo(orgId);
        model.setTimeoutExpress(AiHuAlipayConfig.timeout_express);
        model.setTotalAmount("0.01");
        model.setProductCode(AiHuAlipayConfig.product_code);
        model.setPassbackParams(getPassbackParams(orgId, payType, money, count,petrolKind,ownerId,petrolNum,actualPayment));
        return model;
    }
}
