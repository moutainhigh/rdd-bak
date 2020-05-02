package com.cqut.czb.bn.service.petrolCoupons;

import com.cqut.czb.bn.entity.dto.petrolCoupons.PetrolCouponsSales;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component
public interface PetrolCouponsSalesRecordsService {
    PageInfo<PetrolCouponsSales> selectPetrolCouponsSalesRecords(PetrolCouponsSales input);

    String getPetrolCouponsSaleMoneyCount(PetrolCouponsSales infoInputDTO);

    Workbook exportCouponsRecords(PetrolCouponsSales inputDTO) throws Exception;
}
