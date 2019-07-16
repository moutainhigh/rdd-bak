package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appMessageManage.ModifyInfoDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppMessageManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * author:De-Qiang Chen
 * function:app's messages management (app消息管理)
 */

@RestController
@RequestMapping("/api/AppMessageManagement")
public class AppMsgManageController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    AppMessageManageService appMessageManageService;

    /**
     * app get all the Messages of the person(获取个人所有的未读信息)
     */
    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    public JSONResult getMessages(Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserId("156225046615657");
        return new JSONResult(appMessageManageService.getMessages(user));
    }

    /**
     * app get the one of the most recently and not be read Message(首页弹窗，获取最近一条未读信息)
     */
    @RequestMapping(value = "/getOnePopMessages", method = RequestMethod.GET)
    public JSONResult getOnePopMessages(Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserId("156225046615657");
        return new JSONResult(appMessageManageService.getOnePopMessages(user));
    }




    /**
     * app's Modify information status(修改信息状态)
     */
    @RequestMapping(value = "/ModifyInfoStatus", method = RequestMethod.GET)
    public JSONResult ModifyInfoStatus(Principal principal, ModifyInfoDTO modifyInfoDTO) {
        User user = (User)redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserId("156225046615657");
//        modifyInfoDTO.setMsgRecordId("1");
        return new JSONResult(appMessageManageService.modifyMessage(user,modifyInfoDTO));
    }

}