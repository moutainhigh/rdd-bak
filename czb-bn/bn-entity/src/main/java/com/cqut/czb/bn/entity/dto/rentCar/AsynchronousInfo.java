package com.cqut.czb.bn.entity.dto.rentCar;

public class AsynchronousInfo {
    // 合同状态，1 未签署完消息 2 合同签署完成消息
    private Integer noticeType;

    // 合同内容
    private String content;

    // 签署者集合
    private SignerMap map;

    public AsynchronousInfo() {
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SignerMap getMap() {
        return map;
    }

    public void setMap(SignerMap map) {
        this.map = map;
    }
}
