package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.dao.mapper.vehicleService.RemotePushMapper;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.RemotePush;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IDictService;
import com.cqut.czb.bn.service.vehicleService.RemotePushService;
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

    @Autowired
    RemotePushService remotePushService;

    @RequestMapping(value = "/selectCustomerServiceStaff",method = RequestMethod.GET)
    public JSONResult selectCustomerServiceStaff(){

        return new JSONResult(dictService.selectCustomerServiceStaff());
    }

    @RequestMapping(value = "/selectAndroidInfo",method = RequestMethod.GET)
    public JSONResult selectAndroidInfo(Principal principal, @Param("version")String version){
        User user = (User)redisUtils.get(principal.getName());

        return new JSONResult(dictService.selectAndroidInfo(user,version));
    }


    @RequestMapping(value = "/selectIOSInfo",method = RequestMethod.GET)
    public JSONResult selectIOSInfo(Principal principal,@Param("version")String version, @Param("DeviceToken")String DeviceToken){
        User user = (User)redisUtils.get(principal.getName());
        if (DeviceToken!=null&&!"".equals(DeviceToken)){
            remotePushService.insertToken(DeviceToken,user,2);
        }



        return new JSONResult(dictService.selectIOSInfo(user,version));
    }

    @RequestMapping(value = "/selectDictList",method = RequestMethod.GET)
    public JSONResult selectDictList(DictInputDTO dictInputDTO, PageDTO pageDTO){

        return new JSONResult(dictService.selectDictList(dictInputDTO, pageDTO));
    }

    @RequestMapping(value = "/updateDict",method = RequestMethod.POST)
    public JSONResult updateDict(DictInputDTO dictInputDTO){

        return new JSONResult(dictService.updateDict(dictInputDTO));
    }

    @RequestMapping(value = "/deleteDict",method = RequestMethod.POST)
    public JSONResult deleteDict(DictInputDTO dictInputDTO){

        return new JSONResult(dictService.deleteDict(dictInputDTO));
    }

    @RequestMapping(value = "/insertDict",method = RequestMethod.POST)
    public JSONResult insertDict(DictInputDTO dictInputDTO){

        return new JSONResult(dictService.insertDict(dictInputDTO));
    }

    @RequestMapping(value = "/getDicByName",method = RequestMethod.GET)
    public JSONResult getDicByName(DictInputDTO dictInputDTO){
        return new JSONResult(dictService.getDicByName(dictInputDTO));
    }
}
