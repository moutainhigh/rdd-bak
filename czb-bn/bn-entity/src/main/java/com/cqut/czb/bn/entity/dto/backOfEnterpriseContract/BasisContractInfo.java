package com.cqut.czb.bn.entity.dto.backOfEnterpriseContract;

public class BasisContractInfo {
    // 合同编号
    private String contractId;

    // 合同签署时间
    private String signedAt;

    // 合同创建时间
    private String createAt;

    // 合同签署状态
    private Integer status;

    // 第三方云合同id
    private String thirdContractId;

    // 总打款金额
    private double payTotal;

    // 存证id
    private String czId;

    // 开户银行
    private String bankDeposit;

    // 银行账号
    private String bankNum;

    // 开户姓名
    private String bankName;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSignedAt() {
        return signedAt;
    }

    public void setSignedAt(String signedAt) {
        this.signedAt = signedAt;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getThirdContractId() {
        return thirdContractId;
    }

    public void setThirdContractId(String thirdContractId) {
        this.thirdContractId = thirdContractId;
    }

    public double getPayTotal() {
        return payTotal;
    }

    public void setPayTotal(double payTotal) {
        this.payTotal = payTotal;
    }

    public String getCzId() {
        return czId;
    }

    public void setCzId(String czId) {
        this.czId = czId;
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
