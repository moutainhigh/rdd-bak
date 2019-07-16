package com.cqut.czb.bn.entity.dto.appMessageManage;

import java.util.List;

/**
 * 含有多少条数据
 */
public class MsgNumDTO {

    private Integer msgNum;

    private List<MsgRecordDTO> msgRecordDTOS;

    public Integer getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(Integer msgNum) {
        this.msgNum = msgNum;
    }

    public List<MsgRecordDTO> getMsgRecordDTOS() {
        return msgRecordDTOS;
    }

    public void setMsgRecordDTOS(List<MsgRecordDTO> msgRecordDTOS) {
        this.msgRecordDTOS = msgRecordDTOS;
    }
}
