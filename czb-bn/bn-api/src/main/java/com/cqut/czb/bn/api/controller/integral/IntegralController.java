package com.cqut.czb.bn.api.controller.integral;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralDistributionDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralExchangeDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralManageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.integral.IntegralService;
import com.cqut.czb.bn.util.RSA.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/08
 */
@RestController
@RequestMapping("/api/integral")
public class IntegralController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    IntegralService integralService;

    /**
     * 获取个人当前总积分
     * @param principal
     * @return
     */
    @RequestMapping(value = "/getCurrentTotalIntegral", method = RequestMethod.GET)
    public JSONResult getCurrentTotalIntegral(Principal principal, String userAccount) {
        User user = (User) redisUtils.get(principal.getName());
        return integralService.getCurrentTotalIntegral(user.getUserId(), userAccount);
    }

    /**
     * 获取个人积分明细
     * @param
     * @return
     */
    @RequestMapping(value = "/getIntegralDetail",method = RequestMethod.GET)
    public JSONResult getIntegralDetail(Principal principal, String userAccount, PageDTO pageDTO) {
        User user = (User) redisUtils.get(principal.getName());

        if (user == null || user.getUserId() == null) {
            return new JSONResult("未登录",500);
        }

        return new JSONResult(integralService.getIntegralDetail(user.getUserId(), userAccount, pageDTO));
    }

    /**
     * 获得赠送好友的明细
     * @param principal
     * @return
     */
    @RequestMapping(value = "/getOfferIntegralDetail",method = RequestMethod.GET)
    public JSONResult getOfferIntegralDetail(Principal principal) {
        User user = (User) redisUtils.get(principal.getName());

        if (user == null || user.getUserId() == null) {
            return new JSONResult("未登录",500);
        }

        return new JSONResult(integralService.getOfferIntegralDetail(user.getUserId()));
    }

    /**
     * 获取兑换码详情
     * @param principal
     * @param integralExchangeId
     * @return
     */
    @RequestMapping(value = "/getExchangeDetails", method = RequestMethod.GET)
    public JSONResult getExchangeDetails(Principal principal, String integralExchangeId) {
        User user = (User) redisUtils.get(principal.getName());

        return integralService.getExchangeDetails(integralExchangeId);
    }

    /**
     * 购买积分
     * @param
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @RequestMapping(value = "/purchaseIntegral",method = RequestMethod.POST)
    public JSONResult purchaseIntegral(Principal principal) {
        return null;
    }

    /**
     * 兑换积分
     * @param
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @RequestMapping(value = "/exchangeIntegral",method = RequestMethod.POST)
    public JSONResult exchangeIntegral(Principal principal,@RequestBody IntegralExchangeDTO integralExchangeDTO) {
        User user = (User) redisUtils.get(principal.getName());
        // 解密兑换码
        try {
            String integralExchangeId = RSAUtils.privateDecrypt(
                    RSAUtils.eXcodeToCipher(integralExchangeDTO.getExchangeCode()),
                    RSAUtils.getPrivateKey(RSAUtils.privateKey));
            integralExchangeDTO.setIntegralExchange(integralExchangeId);
        } catch (NoSuchAlgorithmException e) {
            return new JSONResult("服务器错误,请与管理员联系");
        } catch (InvalidKeySpecException e) {
            return new JSONResult("兑换码不存在!");
        }

        return integralService.exchangeIntegral(integralExchangeDTO, user.getUserId());
    }

    /**
     * 赠送积分
     * @param
     * @return
     */
    @RequestMapping(value = "/giveIntegral",method = RequestMethod.POST)
    public JSONResult giveIntegral(@RequestBody Principal principal) {
        return null;
    }

    /**
     * 商品最高抵扣金额
     * @param
     * @return
     */
    @RequestMapping(value = "/getMaxDeductionAmount", method = RequestMethod.GET)
    public JSONResult getMaxDeductionAmount(String commodityId) {
        return integralService.getMaxDeductionAmount(commodityId);
    }

    /**
     * 积分抵扣记录
     * @param integralInfoDTO
     * @return
     */
    @RequestMapping(value = "/deduction", method = RequestMethod.POST)
    public JSONResult deduction(IntegralInfoDTO integralInfoDTO) {
        integralService.deduction(integralInfoDTO);
        return null;
    }

    /**
     * 通过手机号赠送积分
     * @param principal
     * @param receiverPhone 被赠送人的电话
     * @param integralAmount
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @RequestMapping(value = "/offerIntegralByPhone", method = RequestMethod.POST)
    public JSONResult offerIntegralByPhone(Principal principal, String receiverPhone, Integer integralAmount) {
        User user = (User)redisUtils.get(principal.getName());
        return integralService.offerIntegralByPhone(user.getUserId(), receiverPhone, integralAmount);
    }

    /**
     * 创建兑换码
     * @param principal
     * @param integralExchange
     * @return
     */
    @RequestMapping(value = "/createExchangeCode", method = RequestMethod.POST)
    public JSONResult createExchangeCode(Principal principal, IntegralExchangeDTO integralExchange ) {
        User user = (User) redisUtils.get(principal.getName());
        integralExchange.setExchangeSourceId(user.getUserId());
        return integralService.createExchangeCode(integralExchange);
    }

    /**
     * 获取所有用户积分信息
     * @param userAccount
     * @return
     */
    @RequestMapping(value = "/selectIntegralInfoList", method = RequestMethod.POST)
    public JSONResult selectIntegralInfoList(String userAccount, PageDTO pageDTO) {
        return new JSONResult(integralService.selectIntegralInfoList(userAccount, pageDTO));
    }

    /**
     * 获取发放积分的全部信息
     * @param userAccount
     * @param integralDistributionDTO
     * @return
     */
    @RequestMapping(value = "getIntegralDistributionDetails", method = RequestMethod.GET)
    public JSONResult getIntegralDistributionDetails(String userAccount, IntegralDistributionDTO integralDistributionDTO) {
        return new JSONResult(integralService.getIntegralDistributionDetails(userAccount, integralDistributionDTO));
    }

    /**
     * 手机号补贴积分
     * @param principal
     * @param receiverPhones
     * @param integralAmount
     * @return
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @RequestMapping(value = "/subsidyIntegralByPhone/{integralAmount}", method = RequestMethod.POST)
    public JSONResult subsidyIntegralByPhone(Principal principal,@PathVariable("integralAmount") Integer integralAmount,@RequestBody List<String> receiverPhones) {
        return integralService.subsidyIntegralByPhone(receiverPhones, integralAmount);
    }

    /**
     * 获取积分购买比率
     * @param principal
     * @return
     */
    @RequestMapping(value = "/getIntegralRate", method = RequestMethod.GET)
    public JSONResult getIntegralRate(Principal principal) {
        return integralService.getIntegralRate();
    }

    /**
     * 更新积分购买比率
     * @param dictInputDTO
     * @return
     */
    @RequestMapping(value = "/updateIntegralRate", method = RequestMethod.POST)
    public JSONResult updateIntegralRate(DictInputDTO dictInputDTO) {
        return integralService.updateIntegralRate(dictInputDTO);
    }

    /**
     * 新增积分购买面额
     * @param integralManageDTO
     * @return
     */
    @RequestMapping(value = "/insertIntegralValue", method = RequestMethod.POST)
    public JSONResult insertIntegralValue(IntegralManageDTO integralManageDTO) {
        return integralService.insertIntegralValue(integralManageDTO);
    }

    /**
     * 修改积分购买面额
     * @param integralManageDTO
     * @return
     */
    @RequestMapping(value = "/updateIntegralValue", method = RequestMethod.POST)
    public JSONResult updateIntegralValue(IntegralManageDTO integralManageDTO) {
        return integralService.updateIntegralValue(integralManageDTO);
    }

    /**
     * 删除积分购买面额
     * @param integralManageDTO
     * @return
     */
    @RequestMapping(value = "/deleteIntegralValue", method = RequestMethod.POST)
    public JSONResult deleteIntegralValue(IntegralManageDTO integralManageDTO) {
        return integralService.deleteIntegralValue(integralManageDTO);
    }

    /**
     * 获取积分购买面额
     * @return
     */
    @RequestMapping(value = "/getIntegralValueList", method = RequestMethod.GET)
    public JSONResult deleteIntegralValue(PageDTO pageDTO) {
        return new JSONResult(integralService.getIntegralValueList(pageDTO));
    }

    /**
     * 模糊查询电话号码
     */
    @RequestMapping(value = "/fuzzyQueryUserPhone", method = RequestMethod.GET)
    public JSONResult fuzzyQueryUserPhone(Principal principal, String phone) {
        return new JSONResult(integralService.fuzzyQueryUserPhone(phone));
    }

    /**
     * 获取兑换码的兑换人的详情
     * @param principal
     * @param userAccount
     * @return
     */
    @RequestMapping(value = "/getExchangeLogDetails", method = RequestMethod.GET)
    public JSONResult getExchangeLogDetails(Principal principal, String userAccount, PageDTO pageDTO) {
        return new JSONResult(integralService.getExchangeLogDetails(userAccount, pageDTO));
    }
}
