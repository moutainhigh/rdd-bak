package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.MyIncomeLogDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PersonalCenterUserDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppPersonalCenterService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 创建人：陈德强
 * 作用：手机app个人中业务处理
 * 创建时间：2019/4/21
 */

@RestController
@RequestMapping("/api/AppPersonalCenter")
public class AppPersonalCenterController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    AppPersonalCenterController appPersonalCenterController;

    @Autowired
    AppPersonalCenterService appPersonalCenterService;

    /**
     * app通过useId获取用户收益信息
     *
     * @param principal
     * @return
     */
    @RequestMapping(value = "/selectWallet", method = RequestMethod.GET)
    public JSONResult selectWallet(Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        return new JSONResult(appPersonalCenterService.selectUserIncomeInfo(user.getUserId()));
    }

    @RequestMapping(value = "/getShareInfo", method = RequestMethod.GET)
    public JSONResult getShareInfo(Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        return new JSONResult(user.getUserAccount());
    }

    @RequestMapping(value = "/getMyGTPetrolList", method = RequestMethod.GET)
    public JSONResult getMyGTPetrolList(Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        return new JSONResult(appPersonalCenterService.getGTSoldPetrolForUser(user.getUserId()));

    }

    @RequestMapping(value = "/getMyPhysicalPetrolList", method = RequestMethod.GET)
    public JSONResult getMyPetrolList(Principal principal, String petrolKind) {
        User user = (User) redisUtils.get(principal.getName());
        if (petrolKind.equals("3")){
            return new JSONResult(appPersonalCenterService.getCouponsSaleRecords(user.getUserId()));
        }
        else{
            return new JSONResult(appPersonalCenterService.getPhysicalCardRechargeRecords(user.getUserId(), petrolKind));
        }
    }

    /**
     * 将该用户拥有的的油卡的卡号由本平台分发的卡号修改为国通或其它平台的卡号
     *
     * @param principal
     * @param inputDTO
     * @return
     */
    @RequestMapping(value = "/modifyPetrolNum", method = RequestMethod.POST)
    public JSONResult modifyOilCardNumber(Principal principal, @RequestBody PetrolRechargeInputDTO inputDTO) {
        User user = (User) redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return appPersonalCenterService.modifyPetrolNum(user.getUserId(), inputDTO);
    }

    /**
     * * 获取菜单信息（路由）
     *
     * @return
     */
    @RequestMapping(value = "/getPersonalCenterMenu", method = RequestMethod.GET)
    public JSONResult getPersonalCenterMenu(AppRouterDTO appRouterDTO) {
        return new JSONResult(appPersonalCenterService.getAppRouters(appRouterDTO));
    }

    /**
     * 获取User信息
     */
    @RequestMapping(value = "/appGetUserInfo", method = RequestMethod.GET)
    public JSONResult appGetUserInfo(Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        user.setUserPsw("");//不返回密码
        return new JSONResult(user);
    }

    /**
     * 获取User信息及企业信息
     */
    @RequestMapping(value = "/appGetUserEnterpriseInfo", method = RequestMethod.GET)
    public JSONResult appGetUserEnterpriseInfo(@Param("area") String area, Principal principal, String version) {
        User user = (User) redisUtils.get(principal.getName());
        return new JSONResult(appPersonalCenterService.getUserEnterpriseInfo(user, area));
    }

    /**
     * 修改企业联系人
     */
    @RequestMapping(value = "/appModifyContactInfo", method = RequestMethod.POST)
    public JSONResult appModifyContactInfo(Principal principal, @RequestBody PersonalCenterUserDTO personalCenterUserDTO) {
        User user = (User) redisUtils.get(principal.getName());
        if (user == null || personalCenterUserDTO == null) {
            System.out.println(personalCenterUserDTO == null);
            return null;
        }
        personalCenterUserDTO.setUserAccount(user.getUserAccount());
        personalCenterUserDTO.setUserId(user.getUserId());
        return new JSONResult(appPersonalCenterService.ModifyContactInfo(personalCenterUserDTO));
    }

    /**
     * 个人返佣信息记录
     */
    @RequestMapping(value = "/getFanYonginfo", method = RequestMethod.GET)
    public JSONResult getFanYonginfo(Principal principal, MyIncomeLogDTO myIncomeLogDTO) {
        User user = (User) redisUtils.get(principal.getName());
        if (user == null && myIncomeLogDTO == null) {
            return null;
        }
        myIncomeLogDTO.setUserId(user.getUserId());
        return new JSONResult(appPersonalCenterService.selectIncomeLog(myIncomeLogDTO, user));
    }

    @RequestMapping(value = "/getUserIncomeInfo", method = RequestMethod.GET)
    public JSONResult getUserIncomeInfo(Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        if (user == null) {
            return null;
        }
        return new JSONResult(appPersonalCenterService.selectUserIncomeInfo(user.getUserId()));
    }

}
