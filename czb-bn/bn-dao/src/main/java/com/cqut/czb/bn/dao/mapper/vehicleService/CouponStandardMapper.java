package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CouponStandard;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponStandardMapper {
    int deleteByPrimaryKey(String standardId);

    int insert(CouponStandard record);

    int insertSelective(CouponStandard record);

    CouponStandard selectByPrimaryKey(String standardId);

    int updateByPrimaryKeySelective(CouponStandard record);

    int updateByPrimaryKey(CouponStandard record);
}