package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.appMessageManage.ModifyInfoDTO;
import com.cqut.czb.bn.entity.dto.appMessageManage.MsgRecordDTO;
import com.cqut.czb.bn.entity.entity.User;

import java.util.List;

public interface AppMessageManageService {

    /**
     * app get all the Messages of the person(获取个人所有的未读信息)
     */
    List<MsgRecordDTO> getMessages(User user);

    /**
     * app get the one of the most recently and not be read Message(首页弹窗，获取最近一条未读信息)
     */
    MsgRecordDTO getOnePopMessages(User user);

    /**
     * modify the message
     */
    boolean modifyMessage(User user, ModifyInfoDTO modifyInfoDTO);
}
