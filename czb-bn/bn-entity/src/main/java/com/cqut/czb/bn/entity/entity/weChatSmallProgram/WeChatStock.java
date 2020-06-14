package com.cqut.czb.bn.entity.entity.weChatSmallProgram;

import java.util.Date;

public class WeChatStock {
    private String  stockId;
    private String commodityId;
    private String stockAttrId;
    private String content;
    private String state;
    private Date createAt;
    private Date updateAt;
    private String buyerId;
    private String stockAttrIds;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getStockAttrId() {
        return stockAttrId;
    }

    public void setStockAttrId(String stockAttrId) {
        this.stockAttrId = stockAttrId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getStockAttrIds() {
        return stockAttrIds;
    }

    public void setStockAttrIds(String stockAttrIds) {
        this.stockAttrIds = stockAttrIds;
    }
}
