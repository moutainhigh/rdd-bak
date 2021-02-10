package com.cqut.czb.bn.service.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralExchangeDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import java.util.List;

public interface IntegralService {

    /**
     * 积分明细
     */
    List<IntegralLogDTO> getIntegralDetail(String userId);

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
}
