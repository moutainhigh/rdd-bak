package com.cqut.czb.bn.entity.dto.wechatAppletCommodity;

import com.cqut.czb.bn.entity.dto.shop.FileFunctionDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class WxAttributeDTO {
    private String commmodityAttrId;
    private double extraFyMoney;
    private String commodityId;
    private double extraSaleMoney;
    private String fileId;
    private int attrOrder;
    private String attributeId;
    private String name;
    private String content;
    private List<FileFunctionDTO> imgs;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    public String getCommmodityAttrId() {
        return commmodityAttrId;
    }

    public void setCommmodityAttrId(String commmodityAttrId) {
        this.commmodityAttrId = commmodityAttrId;
    }

    public double getExtraFyMoney() {
        return extraFyMoney;
    }

    public void setExtraFyMoney(double extraFyMoney) {
        this.extraFyMoney = extraFyMoney;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public double getExtraSaleMoney() {
        return extraSaleMoney;
    }

    public void setExtraSaleMoney(double extraSaleMoney) {
        this.extraSaleMoney = extraSaleMoney;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public int getAttrOrder() {
        return attrOrder;
    }

    public void setAttrOrder(int attrOrder) {
        this.attrOrder = attrOrder;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
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

    public List<FileFunctionDTO> getImgs() {
        return imgs;
    }

    public void setImgs(List<FileFunctionDTO> imgs) {
        this.imgs = imgs;
    }
}
