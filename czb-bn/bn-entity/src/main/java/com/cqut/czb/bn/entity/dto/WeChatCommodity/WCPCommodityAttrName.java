package com.cqut.czb.bn.entity.dto.WeChatCommodity;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-12-18 17:13
 */
public class WCPCommodityAttrName {
    private String commodityId;

    private String name;

    private List<WCPCommodityAttrContent> contentList;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WCPCommodityAttrContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<WCPCommodityAttrContent> contentList) {
        this.contentList = contentList;
    }
}