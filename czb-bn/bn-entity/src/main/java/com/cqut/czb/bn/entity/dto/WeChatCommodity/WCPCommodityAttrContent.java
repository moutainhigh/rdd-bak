package com.cqut.czb.bn.entity.dto.WeChatCommodity;

/**
 * @Description
 * @auther nihao
 * @create 2019-12-18 17:00
 */
public class WCPCommodityAttrContent {
    private String commodityAttrId;

    private String extraFyMoney;

    private String commodityId;

    private String attributeId;

    private String extraSaleMoney;

    private String savePath;

    private String name;

    private String content;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getCommodityAttrId() {
        return commodityAttrId;
    }

    public void setCommodityAttrId(String commodityAttrId) {
        this.commodityAttrId = commodityAttrId;
    }

    public String getExtraFyMoney() {
        return extraFyMoney;
    }

    public void setExtraFyMoney(String extraFyMoney) {
        this.extraFyMoney = extraFyMoney;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getExtraSaleMoney() {
        return extraSaleMoney;
    }

    public void setExtraSaleMoney(String extraSaleMoney) {
        this.extraSaleMoney = extraSaleMoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}