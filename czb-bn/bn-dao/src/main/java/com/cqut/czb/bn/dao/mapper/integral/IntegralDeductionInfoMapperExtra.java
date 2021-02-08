package com.cqut.czb.bn.dao.mapper.integral;

public interface IntegralDeductionInfoMapperExtra {

    /**
     * 获取最高抵扣额度
     * @param commodityId
     * @return
     */
    Double getMaxDeductionAmount(String commodityId);
}
