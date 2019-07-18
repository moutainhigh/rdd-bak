package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.MessageManagement.MessageListDTO;
import com.cqut.czb.bn.entity.entity.MsgModel;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.MessageManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-17 10:08
 */
@RestController
@RequestMapping("/api/messageManagement")
public class MessageManagement {

    @Autowired
    MessageManagementService messageManagementService;

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/getMessageList")
    public JSONResult getMessageList(MessageListDTO messageListDTO){
        return new JSONResult(messageManagementService.getMessageList(messageListDTO));
    }

    @PostMapping("/deleteMsgModelById")
    public JSONResult deleteMsgModelById(String msgModelId){
        return new JSONResult(messageManagementService.deleteMsgModelById(msgModelId));
    }


    @PostMapping("/createMsgModel")
    public JSONResult createMsgModel(MsgModel msgModel, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        msgModel.setAnnouncerId(user.getUserId());
        return new JSONResult(messageManagementService.createMsgModel(msgModel));
    }

    @PostMapping("/sendMessage")
    public JSONResult sendMessage(String msgModelId,Integer receiverType){
        return new JSONResult(messageManagementService.sendMessage(msgModelId, receiverType));
    }
}