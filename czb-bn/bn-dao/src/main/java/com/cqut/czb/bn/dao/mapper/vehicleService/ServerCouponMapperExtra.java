package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.IssueCoupons.IssueCouponsDTO;
import com.cqut.czb.bn.entity.dto.appCarWash.conpons;
import com.cqut.czb.bn.entity.dto.vehicleService.ServerCouponDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServerCouponMapperExtra {
    List<ServerCouponDTO> selectByPrimaryKey(ServerCouponDTO serverCouponDTO);

    int updateExpire(ServerCouponDTO serverCouponDTO);

    List<conpons> selectCoupons(@Param("userId") String userId, @Param("couponId") String couponId);

    int updateCoupons(@Param("couponId") String couponId);

    List<IssueCouponsDTO> selectAllCouponsInfo();
}
