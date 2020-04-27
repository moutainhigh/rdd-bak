package com.cqut.czb.bn.service.impl.petrolCoupons;

import com.cqut.czb.bn.dao.mapper.petrolCoupons.PetrolCouponsSalesRecordsMapper;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.entity.petrolCoupons.PetrolCouponsSalesRecords;
import com.cqut.czb.bn.service.petrolCoupons.PetrolCouponsSalesRecordsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetrolCouponsSalesRecordsServiceImpl implements PetrolCouponsSalesRecordsService {

    @Autowired
    PetrolCouponsSalesRecordsMapper petrolCouponsSalesRecordsMapper;

    @Override
    public PageInfo<PetrolCouponsSalesRecords> selectPetrolCouponsSalesRecords(GetPetrolSaleInfoInputDTO inputDTO) {
        PageHelper.startPage(inputDTO.getCurrentPage(), inputDTO.getPageSize());
        return new PageInfo<>(petrolCouponsSalesRecordsMapper.selectPetrolCouponsSalesRecords(inputDTO));
    }
}
