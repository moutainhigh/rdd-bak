package com.cqut.czb.bn.entity.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CommodityStockDTO extends PageDTO{
    private int itemNo;

    private String name;

    private String commodityID;

    private int stockNum;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStockNum() {
        return stockNum;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
