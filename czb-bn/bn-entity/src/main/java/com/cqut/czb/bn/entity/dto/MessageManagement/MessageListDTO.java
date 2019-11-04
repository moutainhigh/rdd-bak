package com.cqut.czb.bn.entity.dto.MessageManagement;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-17 11:40
 */
public class MessageListDTO extends PageDTO {
    /**
     * 消息ID
     */
    private String messageModelId;

    /**
     * 消息标题
     */
    private String messageTitle;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 消息发送时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date messageSendTime;

    /**
     * 消息截止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date messageDeadline;

    /**
     * 消息开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date messageStartTime;

    /**
     * 消息结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date messageEndTime;

    /**
     * 接受人类型
     */
    private Integer receiverType;

    /**
     * 是否弹窗
     */
    private Integer alter;

    /**
     * 是否发送
     */
    private Integer isSend;

    /**
     *通知人ID
     */
    private String announcerId;

    private Integer msgType;

    private Date endTime;

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getAnnouncerId() {
        return announcerId;
    }

    public void setAnnouncerId(String announcerId) {
        this.announcerId = announcerId;
    }

    public Date getMessageDeadline() {
        return messageDeadline;
    }

    public void setMessageDeadline(Date messageDeadline) {
        this.messageDeadline = messageDeadline;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Date getMessageStartTime() {
        return messageStartTime;
    }

    public void setMessageStartTime(Date messageStartTime) {
        this.messageStartTime = messageStartTime;
    }

    public Date getMessageEndTime() {
        return messageEndTime;
    }

    public void setMessageEndTime(Date messageEndTime) {
        this.messageEndTime = messageEndTime;
    }

    public String getMessageModelId() {
        return messageModelId;
    }

    public void setMessageModelId(String messageModelId) {
        this.messageModelId = messageModelId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getMessageSendTime() {
        return messageSendTime;
    }

    public void setMessageSendTime(Date messageSendTime) {
        this.messageSendTime = messageSendTime;
    }

    public Integer getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(Integer receiverType) {
        this.receiverType = receiverType;
    }

    public Integer getAlter() {
        return alter;
    }

    public void setAlter(Integer alter) {
        this.alter = alter;
    }
}