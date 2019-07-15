package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IManagementApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * author：陈德强
 * function：管理api（增删改查）
 */
@RestController
@RequestMapping("/api/ManagementApi")
public class ManagementApiController {

    @Autowired
    private IManagementApiService iManagementApiService;
    /**
     *  api管理——插入关系表
     */
    @RequestMapping(value ="/insertRoleApi",method = RequestMethod.GET)
    public JSONResult insertRoleApi(){
        return new JSONResult(iManagementApiService.insertRoleApi());
    }
}
