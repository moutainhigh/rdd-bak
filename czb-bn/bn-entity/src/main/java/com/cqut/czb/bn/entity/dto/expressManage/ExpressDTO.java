package com.cqut.czb.bn.entity.dto.expressManage;

import java.util.Date;

public class ExpressDTO {
    //快递单id
    private String expressId;
    //快递单号
    private String expressNumber;
    //快递公司
    private String expressCompany;
    //收货人
    private String consignee;
    //发货时间
    private Date sendTime;
    //状态  0未发送   1 派送中   2 已到货
    private int state;

    private Integer pageSize;

    private Integer pageNum;

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expresNumber) {
        this.expressNumber = expresNumber;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
