package com.cqut.czb.bn.service.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralExchangeDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralIogDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import java.util.List;

public interface IntegralService {

    /**
     * 积分明细
     */
    List<IntegralIogDTO> getIntegralDetail(String userId);

    /**
     * 购买积分
     */
    JSONResult purchaseIntegral(IntegralInfoDTO integralInfoDTO);

    /**
     * 兑换积分
     */
    JSONResult exchangeIntegral(IntegralExchangeDTO integralExchangeDTO);

    /**
     * 赠送积分
     */
    JSONResult giveIntegral(IntegralExchangeDTO integralExchangeDTO);

    /**
     * 商品抵扣最高额度
     */
    JSONResult getMaxDeductionAmount(String commodityId);
}
