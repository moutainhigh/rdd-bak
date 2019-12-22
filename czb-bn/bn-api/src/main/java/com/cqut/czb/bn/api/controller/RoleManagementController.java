package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.role.RoleIdDTO;
import com.cqut.czb.bn.entity.dto.role.RoleInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IRoleService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * RoleManagementController 角色管理接口
 * 设计者:   曹渝
 * 更新日期: 2019/04/21
 */
@RestController
@RequestMapping("/api/roleManagement")
public class RoleManagementController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    RedisUtils redisUtils;

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/insertRole",method = RequestMethod.POST)
    public JSONResult insertRole(@Validated @RequestBody RoleInputDTO roleInputDTO){
        boolean isInsert = roleService.insertRole(roleInputDTO);
        if(isInsert) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "新增成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "新增失败");
        }
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/deleteRole",method = RequestMethod.POST)
    public JSONResult deleteRole(@Validated @RequestBody RoleIdDTO roleIdDTO){
        boolean isDelete = roleService.deleteRole(roleIdDTO);
        if(isDelete) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "删除成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "删除失败");
        }
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    public JSONResult updateRole(@Validated  @RequestBody RoleInputDTO roleInputDTO){
        boolean isUpdate = roleService.updateRole(roleInputDTO);
        if(isUpdate) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "更新成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "更新失败");
        }
    }

    @RequestMapping(value = "/selectRole",method = RequestMethod.GET)
    public JSONResult selectRole(@Validated RoleInputDTO roleInputDTO, PageDTO pageDTO){

        return new JSONResult(roleService.selectRole(roleInputDTO, pageDTO));
    }

    @GetMapping("/RoleToPage")
    public JSONResult RoleToPage(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user==null||"".equals(user.getUserId())){
            return new JSONResult();
        }

        return new JSONResult(roleService.getRoleToPage(user));
    }
}

