package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IDictService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/dict")
public class DictController {

    @Autowired
    IDictService dictService;

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/selectCustomerServiceStaff",method = RequestMethod.GET)
    public JSONResult selectCustomerServiceStaff(){

        return new JSONResult(dictService.selectCustomerServiceStaff());
    }

    @RequestMapping(value = "/selectAndroidInfo",method = RequestMethod.GET)
    public JSONResult selectAndroidInfo(Principal principal, @Param("version")String version,@Param("DeviceToken")String DeviceToken){
        User user = (User)redisUtils.get(principal.getName());

        return new JSONResult(dictService.selectAndroidInfo(user,version,DeviceToken));
    }


    @RequestMapping(value = "/selectIOSInfo",method = RequestMethod.GET)
    public JSONResult selectIOSInfo(Principal principal,@Param("version")String version, @Param("DeviceToken")String DeviceToken){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(dictService.selectIOSInfo(user,version, DeviceToken));
    }

    @RequestMapping(value = "/selectDictList",method = RequestMethod.GET)
    public JSONResult selectDictList(DictInputDTO dictInputDTO, PageDTO pageDTO){

        return new JSONResult(dictService.selectDictList(dictInputDTO, pageDTO));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/updateDict",method = RequestMethod.POST)
    public JSONResult updateDict(DictInputDTO dictInputDTO){

        return new JSONResult(dictService.updateDict(dictInputDTO));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/deleteDict",method = RequestMethod.POST)
    public JSONResult deleteDict(DictInputDTO dictInputDTO){

        return new JSONResult(dictService.deleteDict(dictInputDTO));
    }

    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/insertDict",method = RequestMethod.POST)
    public JSONResult insertDict(DictInputDTO dictInputDTO){

        return new JSONResult(dictService.insertDict(dictInputDTO));
    }

    @RequestMapping(value = "/getDicByName",method = RequestMethod.GET)
    public JSONResult getDicByName(DictInputDTO dictInputDTO){
        return new JSONResult(dictService.getDicByName(dictInputDTO));
    }
}
