package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.MessageManagement.MessageListDTO;
import com.cqut.czb.bn.entity.entity.MsgModel;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-17 11:31
 */
@Service
public interface MessageManagementService {
    PageInfo getMessageList(MessageListDTO messageListDTO);

    Boolean deleteMsgModelById(String msgModelId);

    Boolean createMsgModel(MsgModel msgModel);

    Boolean editMsgModel(MsgModel msgModel);

    Boolean sendMessage(String msgModelId);

    Boolean sendMessage(String msgModelId, Map<String, String> content);

    boolean sendMessageToOne(Map<String, String> maps, String userId);
}
