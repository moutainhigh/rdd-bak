package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class MsgModel {
    private String msgModelId;

    private String announcerId;

    private String msgTitle;

    private String msgContent;

    private Date createAt;

    private Date updateAt;

    private Integer msgType;

    private Date endtime;

    private Integer altert;

    public String getMsgModelId() {
        return msgModelId;
    }

    public void setMsgModelId(String msgModelId) {
        this.msgModelId = msgModelId == null ? null : msgModelId.trim();
    }

    public String getAnnouncerId() {
        return announcerId;
    }

    public void setAnnouncerId(String announcerId) {
        this.announcerId = announcerId == null ? null : announcerId.trim();
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle == null ? null : msgTitle.trim();
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
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

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getAltert() {
        return altert;
    }

    public void setAltert(Integer altert) {
        this.altert = altert;
    }
}