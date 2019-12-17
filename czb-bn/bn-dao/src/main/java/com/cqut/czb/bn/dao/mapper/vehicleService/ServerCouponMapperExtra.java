package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.IssueCoupons.IssueCouponsDTO;
import com.cqut.czb.bn.entity.dto.appCarWash.conpons;
import com.cqut.czb.bn.entity.dto.vehicleService.IssueServerCouponDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.ServerCouponDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.CouponStandard;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerCouponMapperExtra {
    int insert(IssueServerCouponDTO issueServerCouponDTO);

    int insertByList(List<IssueServerCouponDTO> list);

    List<ServerCouponDTO> selectByPrimaryKey(ServerCouponDTO serverCouponDTO);

    List<ServerCouponDTO> appSelectByGroup(ServerCouponDTO serverCouponDTO);

    int updateExpire(ServerCouponDTO serverCouponDTO);

    List<conpons> selectCoupons(@Param("userId") String userId, @Param("couponId") String couponId);

    int updateCoupons(@Param("couponId") String couponId);

    int updateCouponToNotUse(@Param("couponId") String couponId);

    List<IssueCouponsDTO> selectAllCouponsInfo(IssueCouponsDTO issueCouponsDTO);
}
