package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerCouponMapper {
    int deleteByPrimaryKey(String couponId);

    int insert(ServerCoupon record);

    int insertSelective(ServerCoupon record);

    ServerCoupon selectByPrimaryKey(String couponId);

    int updateByPrimaryKeySelective(ServerCoupon record);

    int updateByPrimaryKey(ServerCoupon record);
}