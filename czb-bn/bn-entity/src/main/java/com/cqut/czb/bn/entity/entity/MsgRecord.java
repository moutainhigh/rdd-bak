package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class MsgRecord {
    private String msgRecordId;

    private String msgModelId;

    private String msgAnnouncerId;

    private String msgReceiverId;

    private Integer msgState;

    private Integer alert;

    private Date createAt;

    private Date updateAt;

    private String content;

    public String getMsgRecordId() {
        return msgRecordId;
    }

    public void setMsgRecordId(String msgRecordId) {
        this.msgRecordId = msgRecordId == null ? null : msgRecordId.trim();
    }

    public String getMsgModelId() {
        return msgModelId;
    }

    public void setMsgModelId(String msgModelId) {
        this.msgModelId = msgModelId == null ? null : msgModelId.trim();
    }

    public String getMsgAnnouncerId() {
        return msgAnnouncerId;
    }

    public void setMsgAnnouncerId(String msgAnnouncerId) {
        this.msgAnnouncerId = msgAnnouncerId == null ? null : msgAnnouncerId.trim();
    }

    public String getMsgReceiverId() {
        return msgReceiverId;
    }

    public void setMsgReceiverId(String msgReceiverId) {
        this.msgReceiverId = msgReceiverId == null ? null : msgReceiverId.trim();
    }

    public Integer getMsgState() {
        return msgState;
    }

    public void setMsgState(Integer msgState) {
        this.msgState = msgState;
    }

    public Integer getAlert() {
        return alert;
    }

    public void setAlert(Integer alert) {
        this.alert = alert;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}