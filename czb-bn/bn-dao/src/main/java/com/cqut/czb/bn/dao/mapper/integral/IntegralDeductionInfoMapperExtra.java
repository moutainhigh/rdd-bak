package com.cqut.czb.bn.dao.mapper.integral;

import com.cqut.czb.bn.entity.dto.integral.IntegralDeductionInfoDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralDeductionInfo;

import java.util.List;

public interface IntegralDeductionInfoMapperExtra {

    /**
     * 获取最高抵扣额度
     * @param integralDeductionInfoDTO
     * @return
     */
    Double getMaxDeductionAmount(IntegralDeductionInfoDTO integralDeductionInfoDTO);

    int insertRecord(IntegralDeductionInfoDTO record);

    IntegralDeductionInfo selectByCommodityIdAndCommodityAttrId(IntegralDeductionInfo integralDeductionInfo);

    List<IntegralDeductionInfoDTO> selectByCommodityType(IntegralDeductionInfoDTO integralDeductionInfoDTO);
}
