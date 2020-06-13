package com.cqut.czb.bn.entity.entity.weChatOilSale;

import java.util.Date;

public class WXStockAttr {
    private String stockAttrId;

    private String stockId;

    private String attrId;

    private Date createAt;

    private Date updateAt;

    public String getStockAttrId() {
        return stockAttrId;
    }

    public void setStockAttrId(String stockAttrId) {
        this.stockAttrId = stockAttrId == null ? null : stockAttrId.trim();
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId == null ? null : stockId.trim();
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId == null ? null : attrId.trim();
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