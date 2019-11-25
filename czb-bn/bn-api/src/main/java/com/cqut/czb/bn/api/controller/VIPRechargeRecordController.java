package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordListDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.VIPRechargeRecordService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description VIP充值记录管理
 * @auther nihao
 * @create 2019-07-18 22:23
 */

@RestController
@RequestMapping("/api/VIPRechargeRecord")
public class VIPRechargeRecordController {

    @Autowired
    VIPRechargeRecordService vipRechargeRecordService;

    @GetMapping("/getVIPRechargeRecordList")
    public JSONResult getVipRechargeRecordList(VipRechargeRecordListDTO vipRechargeRecordListDTO){
        return new JSONResult(vipRechargeRecordService.getVipRechargeRecordList(vipRechargeRecordListDTO));
    }

    @PostMapping("/deleteVIPRechargeByID")
    public JSONResult deleteVIPRechargeByID(@Param("vipRechargeRecordId")String vipRechargeRecordId){
        return new JSONResult(vipRechargeRecordService.deleteVIPRechargeByID(vipRechargeRecordId));
    }

    @PostMapping("addVipMoney")
    public JSONResult addVipMoney(@RequestBody VipRechargeRecordListDTO vipRechargeRecordListDTO){
        return new JSONResult(vipRechargeRecordService.addVipMoney(vipRechargeRecordListDTO));
    }
}