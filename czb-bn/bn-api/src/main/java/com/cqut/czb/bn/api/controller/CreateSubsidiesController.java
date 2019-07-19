package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.CreateSubsidies.CreateSubsidiesQueryDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.CreateSubsidiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ContractManagementController 新建补贴接口
 * 设计者:   徐皓东
 * 更新日期: 2019/07/14
 */
@RestController
@RequestMapping("/api/CreateSubsidies")
public class CreateSubsidiesController {


    @Autowired
    CreateSubsidiesService createSubsidiesService;

    @PostMapping("/getPartnerSubordinates")
    public JSONResult getPartnerSubordinates(CreateSubsidiesQueryDTO createSubsidiesQueryDTO){
        return new JSONResult(createSubsidiesService.getPartnerSubordinates(createSubsidiesQueryDTO));
    }
}
