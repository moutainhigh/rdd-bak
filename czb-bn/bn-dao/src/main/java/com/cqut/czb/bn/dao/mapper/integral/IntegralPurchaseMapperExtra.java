package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralPurchaseRecord;
import org.springframework.data.repository.query.Param;

public interface IntegralPurchaseMapperExtra {

    int insertIntegralPurchaseRecord(IntegralPurchaseRecord IntegralPurchaseRecord);

    int insertIntegralLog(IntegralLogDTO integralLogDTO);

    int insertEquityGoodsOrder(EquityPaymentDTO equityPaymentDTO);

    int updateIntegralPurchaseRecord(IntegralPurchaseRecord integralPurchaseRecord);

    int updateIntegralInfo(IntegralInfoDTO integralInfoDTO);
}
