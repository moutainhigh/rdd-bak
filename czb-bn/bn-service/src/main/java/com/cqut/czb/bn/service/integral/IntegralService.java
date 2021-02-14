package com.cqut.czb.bn.service.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralDetailsDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralExchangeDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralExchange;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public interface IntegralService {

    /**
     * 获得用户当前总积分
     * @param userId
     * @return
     */
    JSONResult getCurrentTotalIntegral(String userId);

    /**
     * 积分明细
     */
    List<IntegralDetailsDTO> getIntegralDetail(String userId);

    /**
     * 赠送积分明细
     * @param userId
     * @return
     */
    List<IntegralDetailsDTO> getOfferIntegralDetail(String userId);

    JSONResult getExchangeDetails(String integralExchangeId);

    /**
     * 购买积分
     */
    JSONResult purchaseIntegral(IntegralInfoDTO integralInfoDTO);

    /**
     * 兑换积分
     */
    JSONResult exchangeIntegral(IntegralExchangeDTO integralExchangeDTO, String userId);

    /**
     * 赠送积分
     */
    JSONResult giveIntegral(IntegralExchangeDTO integralExchangeDTO);

    /**
     * 商品抵扣最高额度
     */
    JSONResult getMaxDeductionAmount(String commodityId);

    /**
     * 积分抵扣
     * @param integralInfoDTO
     * @return
     */
    JSONResult deduction(IntegralInfoDTO integralInfoDTO);

    /**
     * 通过电话号码赠送
     * @param providerId
     * @param receiverPhone
     * @param integralAmount
     * @return
     */
    JSONResult offerIntegralByPhone(String providerId, String receiverPhone, Integer integralAmount);

    /**
     * 创建积分兑换码
     * @param integralExchange
     * @return
     */
    JSONResult createExchangeCode(IntegralExchange integralExchange);

    /**
     * 获取所有用户积分信息
     * @param userAccount
     * @return
     */
    List<IntegralInfoDTO> selectIntegralInfoList(String userAccount);
}
