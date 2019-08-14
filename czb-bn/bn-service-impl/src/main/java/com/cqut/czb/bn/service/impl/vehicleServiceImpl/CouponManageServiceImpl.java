package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.vehicleService.CouponStandardMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.CouponStandardMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.ServerCouponDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CouponStandard;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;
import com.cqut.czb.bn.service.vehicleService.CouponManageService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponManageServiceImpl implements CouponManageService {

    @Autowired
    ServerCouponMapperExtra serverCouponMapperExtra;
    @Autowired
    CouponStandardMapperExtra couponStandardMapperExtra;

    @Override
    public List<ServerCouponDTO> getCouponList(User user) {
        return serverCouponMapperExtra.selectByPrimaryKey(user.getUserId());
    }

    @Override
    public PageInfo<CouponStandard> getAllCoupon(CouponStandard couponStandard,PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(couponStandardMapperExtra.selectByPrimaryKey(couponStandard));
    }

    @Override
    public Boolean insertCouponStandard(CouponStandard couponStandard) {
        couponStandard.setCreateAt(new Date());
        couponStandard.setStandardId(StringUtil.createId());
        return couponStandardMapperExtra.insert(couponStandard)>0;
    }

    @Override
    public Boolean updateCouponStandard(CouponStandard couponStandard) {
        couponStandard.setUpdateAt(new Date());
        return couponStandardMapperExtra.updateByPrimaryKeySelective(couponStandard)>0;
    }

    @Override
    public Boolean deleteCouponStandard(CouponStandard couponStandard) {
        return couponStandardMapperExtra.deleteByPrimaryKey(couponStandard.getStandardId())>0;
    }
}
