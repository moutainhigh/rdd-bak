package com.cqut.czb.bn.entity.dto.publishInfoAudit;

public class PublishInfoId {
    // 商品id
    private String infoId;

    // 商品标题
    private String InfoName;

    // 发布时间
    private String publishTime;

    // 审核状态
    private Integer isExamine;

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getInfoName() {
        return InfoName;
    }

    public void setInfoName(String infoName) {
        InfoName = infoName;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Integer getIsExamine() {
        return isExamine;
    }

    public void setIsExamine(Integer isExamine) {
        this.isExamine = isExamine;
    }
}
