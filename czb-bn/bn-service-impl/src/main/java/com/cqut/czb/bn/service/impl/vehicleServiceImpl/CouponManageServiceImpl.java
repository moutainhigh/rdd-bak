package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;
import com.cqut.czb.bn.service.vehicleService.CouponManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponManageServiceImpl implements CouponManageService {

    @Autowired
    ServerCouponMapperExtra serverCouponMapperExtra;

    @Override
    public List<ServerCoupon> getCouponList(User user) {
        return serverCouponMapperExtra.selectByPrimaryKey(user.getUserId());
    }
}
