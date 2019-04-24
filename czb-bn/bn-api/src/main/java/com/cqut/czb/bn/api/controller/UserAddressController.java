package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/userAddress")
public class UserAddressController {
    @Autowired
    RedisUtils redisUtils;




    @RequestMapping(value = "/getAddressList",method = RequestMethod.GET)
    public JSONResult getSaleInfoList(Principal principal) {
        User user = redisUtils.get(principal.getName());
        System.out.println(user.getUserId());
//        return new JSONResult(petrolManagementService.getPetrolSaleInfoList(inputDTO));
        return null;
    }
}
