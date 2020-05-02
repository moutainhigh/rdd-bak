package com.cqut.czb.bn.dao.mapper.petrolCoupons;

import com.cqut.czb.bn.entity.entity.petrolCoupons.PetrolCouponsSalesRecords;

public interface PetrolCouponsSalesRecordsMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(PetrolCouponsSalesRecords record);

    int insertSelective(PetrolCouponsSalesRecords record);

    PetrolCouponsSalesRecords selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(PetrolCouponsSalesRecords record);

    int updateByPrimaryKey(PetrolCouponsSalesRecords record);
}