package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.VipArea.VipAreaDTO;
import com.cqut.czb.bn.entity.dto.VipArea.VipChangeType;
import com.cqut.czb.bn.entity.dto.petrolCoupons.PetrolCouponsSales;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.VIPAreaManagementService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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

    @Autowired
    private RedisUtils redisUtils;

    @Value("${register.common.petrolType}")
    private Integer commonPetrolType;

    @Value("${register.special.petrolType}")
    private Integer specialPetrolType;

    @GetMapping("/getVipAreaList")
    public JSONResult getVIPAreaList(VipAreaDTO vipAreaDTO, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user.getIsSpecial() == 0){
            vipAreaDTO.setIsSpecial(commonPetrolType);
        }
        if (user.getIsSpecial() == 1){
            vipAreaDTO.setIsSpecial(specialPetrolType);
        }
        return new JSONResult(vipAreaManagementService.getVipAreaList(vipAreaDTO));
    }

    @PostMapping("/addVipArea")
    public  JSONResult addVipArea(VipAreaDTO vipAreaDTO,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        if (user.getIsSpecial() == 0){
            vipAreaDTO.setIsSpecial(commonPetrolType);
        }
        if (user.getIsSpecial() == 1){
            vipAreaDTO.setIsSpecial(specialPetrolType);
        }
        return new JSONResult(vipAreaManagementService.addVipArea(vipAreaDTO));
    }

    @PostMapping("/closeAll")
    public  JSONResult closeAll(VipChangeType type,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return vipAreaManagementService.closeAll(type,user.getIsSpecial());
    }

    @PostMapping("/editVipArea")
    public JSONResult editVipArea(VipAreaConfig vipAreaConfig){
        return new JSONResult(vipAreaManagementService.editVipArea(vipAreaConfig));
    }

    @PostMapping("/deleteVipArea")
    public JSONResult deleteVipArea(@Param("vipAreaId")String vipAreaId){
        return new JSONResult(vipAreaManagementService.deleteVipArea(vipAreaId));
    }

    @GetMapping("/getVipPriceAndNote")
    public JSONResult getVipPrice(@Param("area")String area,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(vipAreaManagementService.getVipPriceAndNote(area,user.getIsSpecial()));
    }
}