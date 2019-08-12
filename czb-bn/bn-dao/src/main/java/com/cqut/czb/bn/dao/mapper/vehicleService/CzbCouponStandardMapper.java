package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CzbCouponStandard;

public interface CzbCouponStandardMapper {
    int deleteByPrimaryKey(String standardId);

    int insert(CzbCouponStandard record);

    int insertSelective(CzbCouponStandard record);

    CzbCouponStandard selectByPrimaryKey(String standardId);

    int updateByPrimaryKeySelective(CzbCouponStandard record);

    int updateByPrimaryKey(CzbCouponStandard record);
}