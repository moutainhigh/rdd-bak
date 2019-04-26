package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.entity.Address;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.userAddress.IUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/userAddress")
public class UserAddressController {
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    IUserAddressService userAddressService;


    @RequestMapping(value = "/getAddressList",method = RequestMethod.GET)
    public JSONResult getSaleInfoList(Principal principal) {
        User user = (User)redisUtils.get(principal.getName());
        System.out.println();
        return new JSONResult(userAddressService.getUserAddressList(user.getUserId()));
    }

    @RequestMapping(value ="/addAddress", method = RequestMethod.POST)
    public JSONResult addAddress(Address address,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(userAddressService.addAddress(address,user.getUserId()));
    }

    @RequestMapping(value ="/modifyAddress", method = RequestMethod.POST)
    public JSONResult modifyAddress(Address address){
        return new JSONResult(userAddressService.modifyAddress(address));
    }

    @RequestMapping(value ="/deleteAddress", method = RequestMethod.POST)
    public JSONResult deleteAddress(Address address,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(userAddressService.deleteAddress(address,user.getUserId()));
    }

}
