package com.cqut.czb.bn.entity.entity.weChatOilSale;

import java.util.Date;

public class WXStock {
    private String stockId;

    private String commodityId;

    private String stockAttrId;

    private String content;

    private Integer state;

    private Date createAt;

    private Date updateAt;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId == null ? null : stockId.trim();
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId == null ? null : commodityId.trim();
    }

    public String getStockAttrId() {
        return stockAttrId;
    }

    public void setStockAttrId(String stockAttrId) {
        this.stockAttrId = stockAttrId == null ? null : stockAttrId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
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
}