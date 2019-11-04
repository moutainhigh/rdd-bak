package com.cqut.czb.bn.util.config.SendMesConfig;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-03 15:45
 */
public enum MessageModelInfo {
    RECHARGE_SUCCESS_MESSAGE_USER("810579397367427212"),

    DELIVERY_SUCCESS_MESSAGE_USER("810589727705074463");

    private String messageModelInfo;

    public String getMessageModelInfo() {
        return messageModelInfo;
    }

    MessageModelInfo(String messageModelInfo) {
        this.messageModelInfo = messageModelInfo;
    }
}