package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.MessageManagement.MessageListDTO;
import com.cqut.czb.bn.entity.entity.MsgModel;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.MessageManagementService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-17 10:08
 */
@RestController
@RequestMapping("/api/messageManagement")
public class MessageManagementController {

    @Autowired
    MessageManagementService messageManagementService;

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/getMessageList")
    public JSONResult getMessageList(MessageListDTO messageListDTO){
        return new JSONResult(messageManagementService.getMessageList(messageListDTO));
    }

    @PermissionCheck(role = "管理员")
    @PostMapping("/deleteMsgModelById")
    public JSONResult deleteMsgModelById(@Param("msgModelId")String msgModelId){
        return new JSONResult(messageManagementService.deleteMsgModelById(msgModelId));
    }

    @PermissionCheck(role = "管理员")
    @PostMapping("/createMsgModel")
    public JSONResult createMsgModel(@RequestBody MsgModel msgModel, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        msgModel.setAnnouncerId(user.getUserId());
        return new JSONResult(messageManagementService.createMsgModel(msgModel));
    }

    @PermissionCheck(role = "管理员")
    @PostMapping("/editMsgModel")
    public JSONResult editMsgModel(@RequestBody MsgModel msgModel, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        msgModel.setAnnouncerId(user.getUserId());
        return new JSONResult(messageManagementService.editMsgModel(msgModel));
    }

    @PermissionCheck(role = "管理员")
    @PostMapping("/sendMessage")
    public JSONResult sendMessage(@Param("msgModelId")String msgModelId){
        return new JSONResult(messageManagementService.sendMessage(msgModelId));
    }


    @PostMapping("/sendMessageToOne")
    public JSONResult sendMessageToOne(@RequestBody Map<String, String> map, Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(messageManagementService.sendMessageToOne(map, user.getUserId()));
    }
}