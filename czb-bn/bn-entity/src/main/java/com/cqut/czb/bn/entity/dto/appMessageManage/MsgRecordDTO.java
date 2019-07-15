package com.cqut.czb.bn.entity.dto.appMessageManage;

import java.util.Date;

public class MsgRecordDTO {

    private String msgRecordId;

    private String msgAnnouncerId;

    private String msgReceiverId;

    private Integer msgState;

    private Integer alert;

    private String msgTitle;

    private String msgContent;

    private Date createAt;

    private String createTime;

    public String getMsgRecordId() {
        return msgRecordId;
    }

    public void setMsgRecordId(String msgRecordId) {
        this.msgRecordId = msgRecordId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}