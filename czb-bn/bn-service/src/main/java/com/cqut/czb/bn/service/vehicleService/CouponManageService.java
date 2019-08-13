package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CouponManageService {
    List<ServerCoupon> getCouponList(User user);
}
