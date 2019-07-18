package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.VipArea.VipAreaDTO;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.VIPAreaManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description VIP地区管理
 * @auther nihao
 * @create 2019-07-18 14:34
 */

@RestController
@RequestMapping("/api/VIPAreaManagement")
public class VIPAreaManagementController {
    @Autowired
    VIPAreaManagementService vipAreaManagementService;

    @GetMapping("/getVipAreaList")
    public JSONResult getVIPAreaList(VipAreaDTO vipAreaDTO){
        return new JSONResult(vipAreaManagementService.getVipAreaList(vipAreaDTO));
    }

    @PostMapping("/addVipArea")
    public  JSONResult addVipArea(VipAreaDTO vipAreaDTO){
        return new JSONResult(vipAreaManagementService.addVipArea(vipAreaDTO));
    }

    @PostMapping("/editVipArea")
    public JSONResult editVipArea(VipAreaConfig vipAreaConfig){
        return new JSONResult(vipAreaManagementService.editVipArea(vipAreaConfig));
    }
}