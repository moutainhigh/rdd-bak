package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.address.AddressInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.userAddress.IUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
    public JSONResult addAddress(@RequestBody AddressInputDTO address, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
//        try {
//            address = new ObjectMapper().readValue(request.getInputStream(), Address.class);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        return new JSONResult(userAddressService.addAddress(address,user.getUserId()));
    }

    @RequestMapping(value ="/modifyAddress", method = RequestMethod.POST)
    public JSONResult modifyAddress(@RequestBody AddressInputDTO address){
        return new JSONResult(userAddressService.modifyAddress(address));
    }

    @RequestMapping(value ="/deleteAddress", method = RequestMethod.POST)
    public JSONResult deleteAddress(@RequestBody String  addressId,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(userAddressService.deleteAddress(addressId,user.getUserId()));
    }

    @RequestMapping(value="/setDefaultAddress",method=RequestMethod.POST)
    public JSONResult setDefaultAddress(@RequestBody AddressInputDTO address, Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(userAddressService.setDefaultAddress(address.getAddressId(), user.getUserId()));
    }

    @RequestMapping(value = "getDefaultAddress",method = RequestMethod.GET)
    public JSONResult getDefaultAddress(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(userAddressService.getDefaultAddress(user.getUserId()));
    }


}
