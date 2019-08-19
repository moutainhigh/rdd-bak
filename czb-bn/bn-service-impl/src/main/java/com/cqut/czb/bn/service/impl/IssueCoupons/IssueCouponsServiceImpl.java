package com.cqut.czb.bn.service.impl.IssueCoupons;

import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.entity.dto.IssueCoupons.IssueCouponsDTO;
import com.cqut.czb.bn.service.IssueCouponsService.IssueCouponsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueCouponsServiceImpl implements IssueCouponsService {

    @Autowired
    ServerCouponMapperExtra serverCouponMapperExtra;

    @Override
    public List<IssueCouponsDTO> selectCoupons() {
        return null;
    }


}
