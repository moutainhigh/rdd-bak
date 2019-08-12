package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CzbServerCoupon;

public interface CzbServerCouponMapper {
    int deleteByPrimaryKey(String couponId);

    int insert(CzbServerCoupon record);

    int insertSelective(CzbServerCoupon record);

    CzbServerCoupon selectByPrimaryKey(String couponId);

    int updateByPrimaryKeySelective(CzbServerCoupon record);

    int updateByPrimaryKey(CzbServerCoupon record);
}