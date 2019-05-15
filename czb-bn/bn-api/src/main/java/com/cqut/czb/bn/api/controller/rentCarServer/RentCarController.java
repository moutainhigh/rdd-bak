package com.cqut.czb.bn.api.controller.rentCarServer;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.rentCar.AddCompanyContractList;
import com.cqut.czb.bn.entity.dto.rentCar.AddPersonContractInputDTO;
import com.cqut.czb.bn.entity.dto.rentCar.OneContractInfoInputDTO;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.ContractIdInfo;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.rentCarService.RentCarService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/renCar")
@RestController
public class RentCarController {
    @Autowired
    private RentCarService rentCarService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 获取个人收益记录
     * @return JSONResult
     */
    @RequestMapping(value = "/getPersonIncome", method = RequestMethod.GET)
    public JSONResult getPersonIncome(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(rentCarService.getPersonIncome(user.getUserId()));
    }

    /**
     * 获取个人合同概要信息
     * @param oneContractInfoInputDTO
     * @return JSONResult
     */
    @RequestMapping(value = "/getOneContractInfo", method = RequestMethod.POST)
    public JSONResult getOneContractInfo(Principal principal, @RequestBody OneContractInfoInputDTO oneContractInfoInputDTO){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(rentCarService.getOneContractInfo(user.getUserId(), oneContractInfoInputDTO.getContractId()));
    }

    /**
     * 个人租车服务，合同列表获取
     */
    @RequestMapping(value = "/getPersonalContractList", method = RequestMethod.GET)
    public JSONResult getPersonalContractList(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(rentCarService.getPersonalContractList(user.getUserId()));
    }

    /**
     * 企业合同服务，合同列表获取
     */
    @RequestMapping(value = "/getCompanyContractList", method = RequestMethod.GET)
    public JSONResult getCompanyContractList(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(rentCarService.getCompanyContractList(user.getUserId()));
    }

    /**
     * 企业合同概要信息列表获取
     */
    @RequestMapping(value = "/getCompanyPersonList", method = RequestMethod.POST)
    public JSONResult getCompanyPersonList(@RequestBody ContractIdInfo idInfo){
        return new JSONResult(rentCarService.getOneCompanyContractInfo(idInfo.getContractId()));
    }

    /**
     * 获取个人收益
     */
    @RequestMapping(value = "/getPersonalIncome", method = RequestMethod.GET)
    public JSONResult getPersonalIncome(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(rentCarService.getPersonalIncome(user.getUserId()));
    }
}
