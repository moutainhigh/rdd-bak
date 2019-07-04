package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class MsgModel {
    private String msgModelId;

    private String announcerId;

    private String msgTitle;

    private String msgContent;

    private Date createAt;

    private Date updateAt;

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
}