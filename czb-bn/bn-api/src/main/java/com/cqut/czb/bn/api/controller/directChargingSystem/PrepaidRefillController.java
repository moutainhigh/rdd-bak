package com.cqut.czb.bn.api.controller.directChargingSystem;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingCommodityDto;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingCommodity;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/prepaidRefill")
public class PrepaidRefillController {
    @Autowired
    PrepaidRefillService prepaidRefillService;

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/getAllCommodity")
    public JSONResult getAllCommodity(DirectChargingCommodity directChargingCommodity, PageDTO pageDTO){
        return new JSONResult(prepaidRefillService.getAllCommodity(directChargingCommodity,pageDTO));
    }

    @GetMapping("/addCommodity")
    public JSONResult addCommodity(DirectChargingCommodity directChargingCommodity){
        return new JSONResult(prepaidRefillService.addCommodity(directChargingCommodity));
    }

    @GetMapping("/updateCommodity")
    public JSONResult updateCommodity(DirectChargingCommodityDto directChargingCommodityDto){
        return new JSONResult(prepaidRefillService.updateCommodity(directChargingCommodityDto));
    }

    @GetMapping("/deletedCommodity")
    public JSONResult deletedCommodity(String commodityId){
        return prepaidRefillService.deletedCommodity(commodityId);
    }

    @GetMapping("/saleStatusCommodity")
    public JSONResult saleStatusCommodity(String commodityIds, Integer state){
        return prepaidRefillService.saleStatusCommodity(commodityIds,state);
    }

    /**
     * 获取当前账户号码和绑定的油卡
     * @param principal
     * @return
     */
    @PostMapping("/getInfoNum")
    public JSONResult getInfoNum(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(prepaidRefillService.getInfoNum(user.getUserId()));
    }
}
