package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IDepositRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/depositRecords")
public class DepositRecordsController {

    @Autowired
    IDepositRecordsService depositRecordsService;

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/selectDepositRecords",method = RequestMethod.GET)
    public JSONResult selectDepositRecords(Principal principal, PageDTO pageDTO){
        User user = (User)redisUtils.get(principal.getName());

        return new JSONResult(depositRecordsService.selectDepositRecords(user, pageDTO));
    }
}
