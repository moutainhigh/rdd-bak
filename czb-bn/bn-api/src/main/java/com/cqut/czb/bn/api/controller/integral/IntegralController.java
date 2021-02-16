package com.cqut.czb.bn.api.controller.integral;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.integral.IntegralExchangeDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchange;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.integral.IntegralService;
import com.cqut.czb.bn.util.RSA.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

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
    public JSONResult getCurrentTotalIntegral(Principal principal) {
        User user = (User) redisUtils.get(principal.getName());
        return integralService.getCurrentTotalIntegral(user.getUserId());
    }

    /**
     * 获取个人积分明细
     * @param
     * @return
     */
    @RequestMapping(value = "/getIntegralDetail",method = RequestMethod.GET)
    public JSONResult getIntegralDetail(Principal principal) {
        User user = (User) redisUtils.get(principal.getName());

        if (user == null || user.getUserId() == null) {
            return new JSONResult("未登录",500);
        }

        return new JSONResult(integralService.getIntegralDetail(user.getUserId()));
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
    public JSONResult exchangeIntegral(Principal principal,IntegralExchangeDTO integralExchangeDTO) {
        User user = (User) redisUtils.get(principal.getName());
        // 解密兑换码
        try {
            String integralExchangeId = RSAUtils.privateDecrypt(
                    RSAUtils.eXcodeToCipher(integralExchangeDTO.getExchangeCode()),
                    RSAUtils.getPrivateKey(RSAUtils.privateKey));
            integralExchangeDTO.setIntegralExchange(integralExchangeId);
        } catch (NoSuchAlgorithmException e) {
            return new JSONResult("服务器错误,请与管理员联系", 500);
        } catch (InvalidKeySpecException e) {
            return new JSONResult("兑换码不存在!", 500);
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
    public JSONResult selectIntegralInfoList(String userAccount) {
        return new JSONResult(integralService.selectIntegralInfoList(userAccount));
    }

    /**
     * 手机号补贴积分
     * @param principal
     * @param receiverPhone
     * @param integralAmount
     * @return
     */
    @RequestMapping(value = "/subsidyIntegralByPhone", method = RequestMethod.POST)
    public JSONResult subsidyIntegralByPhone(Principal principal, String receiverPhone, Integer integralAmount) {
        return integralService.subsidyIntegralByPhone(receiverPhone, integralAmount);
    }
}
