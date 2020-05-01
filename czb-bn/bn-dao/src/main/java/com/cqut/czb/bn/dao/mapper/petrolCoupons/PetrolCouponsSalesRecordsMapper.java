package com.cqut.czb.bn.dao.mapper.petrolCoupons;

import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.entity.petrolCoupons.PetrolCouponsSalesRecords;
import java.util.List;

public interface PetrolCouponsSalesRecordsMapper {
    List<PetrolCouponsSalesRecords> selectPetrolCouponsSalesRecords(GetPetrolSaleInfoInputDTO inputDTO);

    String getPetrolCouponsSaleMoneyCount(GetPetrolSaleInfoInputDTO infoInputDTO);
}