package com.cqut.czb.bn.dao.mapper.petrolCoupons;

import com.cqut.czb.bn.entity.dto.petrolCoupons.PetrolCouponsSales;
import java.util.List;

public interface PetrolCouponsSalesMapper {
    List<PetrolCouponsSales> selectPetrolCouponsSalesRecords(PetrolCouponsSales inputDTO);

    String getPetrolCouponsSaleMoneyCount(PetrolCouponsSales infoInputDTO);
}