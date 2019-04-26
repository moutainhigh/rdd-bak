package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.user.UserIdDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IUserService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserManagementController 用户管理接口
 * 设计者:   曹渝
 * 更新日期: 2019/04/24
 */
@RestController
@RequestMapping("/api/userManagement")
public class UserManagementController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    public JSONResult deleteUser(@Validated @RequestBody UserIdDTO userIdDTO){

        boolean isDelete = userService.deleteUser(userIdDTO);
        if(isDelete) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "删除成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "删除失败");
        }
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public JSONResult updateUser(@Validated  @RequestBody UserInputDTO userInputDTO){
        boolean isUpdate = userService.updateUser(userInputDTO);
        if(isUpdate) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "更新成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "更新失败");
        }
    }

    @RequestMapping(value = "/selectUser",method = RequestMethod.GET)
    public JSONResult selectUser(@Validated UserInputDTO userInputDTO, PageDTO pageDTO){

        return new JSONResult(userService.selectUser(userInputDTO, pageDTO));
    }

    @RequestMapping(value = "/assignRole", method = RequestMethod.POST)
    public  JSONResult assignRole(@Validated UserInputDTO userInputDTO){

        boolean isAssign = userService.assignRole(userInputDTO);
        if(isAssign) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "更新角色成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "更新角色失败");
        }
    }
}
