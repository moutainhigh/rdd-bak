package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralDeductionInfoDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralDeductionInfo;

import java.util.List;

public interface IntegralDeductionInfoMapperExtra {

    /**
     * 获取最高抵扣额度
     * @param commodityId
     * @return
     */
    Double getMaxDeductionAmount(String commodityId);

    int insertRecord(IntegralDeductionInfo record);

    IntegralDeductionInfo selectByCommodityId(IntegralDeductionInfo integralDeductionInfo);

    List<IntegralDeductionInfoDTO> selectByCommodityType(IntegralDeductionInfoDTO integralDeductionInfoDTO);
}
