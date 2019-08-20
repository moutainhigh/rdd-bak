package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CouponStandard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponStandardMapperExtra {
    List<CouponStandard> selectByPrimaryKey(CouponStandard couponStandard);

    int insert(CouponStandard couponStandard);

    int updateByPrimaryKeySelective(CouponStandard couponStandard);

    int updateByDelete(CouponStandard couponStandard);

    List<CouponStandard> selectCouponStandardType();
}
