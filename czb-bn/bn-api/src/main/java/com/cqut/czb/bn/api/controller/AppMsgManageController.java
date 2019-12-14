package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.appMessageManage.ModifyInfoDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppMessageManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * author:De-Qiang Chen
 * function:app'a messages management (app消息管理)
 */

@RestController
@RequestMapping("/api/AppMessageManagement")
public class AppMsgManageController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    AppMessageManageService appMessageManageService;

    /**
     * app get Message number(获取未读数量)
     */
    @RequestMapping(value = "/getMsgNum", method = RequestMethod.GET)
    public JSONResult getMsgNum(Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserId("155937282021834");
        return new JSONResult(appMessageManageService.getMsgNum(user));
    }

    /**
     * app get all the Messages of the person(获取个人近期的所有信息)
     */
    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    public JSONResult getMessages(Principal principal, PageDTO pageDTO) {
        User user = (User)redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserId("156225046615657");
        return new JSONResult(appMessageManageService.getMessages(user, pageDTO));
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
     * app'a Modify information status(修改信息状态)
     */
    @RequestMapping(value = "/ModifyInfoStatus", method = RequestMethod.GET)
    public JSONResult ModifyInfoStatus(Principal principal, ModifyInfoDTO modifyInfoDTO) {
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(appMessageManageService.modifyMessage(user,modifyInfoDTO));
    }

}
