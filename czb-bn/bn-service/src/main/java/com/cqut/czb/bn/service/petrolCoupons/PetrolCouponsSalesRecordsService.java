package com.cqut.czb.bn.service.petrolCoupons;

import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.entity.petrolCoupons.PetrolCouponsSalesRecords;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

@Component
public interface PetrolCouponsSalesRecordsService {
    PageInfo<PetrolCouponsSalesRecords> selectPetrolCouponsSalesRecords(GetPetrolSaleInfoInputDTO input);

    String getPetrolCouponsSaleMoneyCount(GetPetrolSaleInfoInputDTO infoInputDTO);

    Workbook exportCouponsRecords(GetPetrolSaleInfoInputDTO inputDTO) throws Exception;
}
