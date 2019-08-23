package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.IssueServerCouponDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.ServerCouponDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CouponStandard;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CouponManageService {
    List<ServerCouponDTO> getCouponList(ServerCouponDTO serverCouponDTO,User user);

    List<ServerCouponDTO> getUseCouponList(ServerCouponDTO serverCouponDTO,User user);

    PageInfo<CouponStandard> getAllCoupon(CouponStandard couponStandard,PageDTO pageDTO);

    Boolean insertCouponStandard(CouponStandard couponStandard);

    Boolean updateCouponStandard(CouponStandard couponStandard);

    Boolean deleteCouponStandard(CouponStandard couponStandard);

    List<CouponStandard> getCouponType();

    PageInfo<ServerCouponDTO> getCouponByUser(ServerCouponDTO serverCouponDTO,PageDTO pageDTO);

    Boolean issueCoupon(IssueServerCouponDTO issueServerCouponDTO);
}
