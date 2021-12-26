package com.cqut.czb.bn.entity.dto;

public class ImportWxStockDTO {

    private String StockID;

    private String StockAtrrID;

    private String AtrrID;

    private String commodityID;

    private String commodityNo;

    private String attribute;

    private String content;

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
    }

    public String getStockID() {
        return StockID;
    }

    public void setStockID(String stockID) {
        StockID = stockID;
    }

    public String getStockAtrrID() {
        return StockAtrrID;
    }

    public void setStockAtrrID(String stockAtrrID) {
        StockAtrrID = stockAtrrID;
    }

    public String getAtrrID() {
        return AtrrID;
    }

    public void setAtrrID(String atrrID) {
        AtrrID = atrrID;
    }

    public String getCommodityNo() {
        return commodityNo;
    }

    public void setCommodityNo(String commodityNo) {
        this.commodityNo = commodityNo;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ImportWxStockDTO{" +
                "StockID='" + StockID + '\'' +
                ", StockAtrrID='" + StockAtrrID + '\'' +
                ", AtrrID='" + AtrrID + '\'' +
                ", commodityID='" + commodityID + '\'' +
                ", commodityNo='" + commodityNo + '\'' +
                ", attribute='" + attribute + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
