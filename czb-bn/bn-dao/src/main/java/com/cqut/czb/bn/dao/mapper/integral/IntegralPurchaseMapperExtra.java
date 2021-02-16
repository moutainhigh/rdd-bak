package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import org.springframework.data.repository.query.Param;

public interface IntegralPurchaseMapperExtra {

    int insertIntegralPurchaseRecord(IntegralRechargeDTO integralRechargeDTO);

    int insertIntegralLog(IntegralLogDTO integralLogDTO);

    int updateIntegralPurchaseRecord(IntegralRechargeDTO integralRechargeDTO);

    int updateIntegralInfo(@Param("currentTotal")int currentTotal, @Param("userId")String userId);
}
