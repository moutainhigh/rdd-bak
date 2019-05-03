package com.cqut.czb.bn.api.controller.rentCarServer;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.rentCar.AddCompanyContractList;
import com.cqut.czb.bn.entity.dto.rentCar.AddPersonContractInputDTO;
import com.cqut.czb.bn.entity.dto.rentCar.OneContractInfoInputDTO;
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
     * 获取合同概要信息
     * @param oneContractInfoInputDTO
     * @return JSONResult
     */
    @RequestMapping(value = "/getOneContractInfo", method = RequestMethod.POST)
    public JSONResult getOneContractInfo(@RequestBody @Validated Principal principal, OneContractInfoInputDTO oneContractInfoInputDTO){
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
    // TODO 此返回列表中的租借有问题，应该是套餐金额
    @RequestMapping(value = "/getCompanyPersonList", method = RequestMethod.GET)
    public JSONResult getCompanyPersonList(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(rentCarService.getOneCompanyContractInfo(user.getUserId()));
    }

    /**
     * 企业合同新增
     */
    @RequestMapping(value = "/addCompanyContract", method = RequestMethod.POST)
    public JSONResult addCompanyContract(@RequestBody Principal principal, AddCompanyContractList addCompanyContractList){
        User user = (User)redisUtils.get(principal.getName());
        int success = rentCarService.addCompanyContract(user.getUserId(), addCompanyContractList);

        return new JSONResult(success);
    }

    /**
     * 个人租约新增
     */
    @RequestMapping(value = "/addPersonContract", method = RequestMethod.POST)
    public JSONResult addPersonContract(@RequestBody Principal principal, AddPersonContractInputDTO inputDTO){
        User user = (User)redisUtils.get(principal.getName());
        JSONResult json = new JSONResult();
        int code = rentCarService.addPersonContract(user.getUserId(), inputDTO.getPersonId(), inputDTO.getIdentifyCode());

        switch (code){
            case 99:
                json.setMessage("用户已签订过，不能重复签订");
                json.setCode(code);
                break;
            case 100:
                json.setMessage("更新签订状态出错");
                json.setCode(code);
                break;
            case 101:
                json.setMessage("更新多条数据签订状态，认证码可能重复");
                json.setCode(code);
                break;
            case 102:
                json.setMessage("插入个人合同记录出错");
                json.setCode(code);
                break;
            case 103:
                json.setMessage("签订失败");
                json.setCode(103);
                break;
            case 1:
                json.setMessage("签订成功");
                json.setCode(code);
                break;
        }

        return json;
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
