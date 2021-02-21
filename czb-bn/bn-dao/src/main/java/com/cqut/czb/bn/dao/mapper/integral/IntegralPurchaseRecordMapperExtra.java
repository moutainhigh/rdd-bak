package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralPurchaseRecordDTO;

import java.util.List;

public interface IntegralPurchaseRecordMapperExtra {

    List<IntegralPurchaseRecordDTO> getIntegralPurchaseList(IntegralPurchaseRecordDTO integralPurchaseRecordDTO);
}
