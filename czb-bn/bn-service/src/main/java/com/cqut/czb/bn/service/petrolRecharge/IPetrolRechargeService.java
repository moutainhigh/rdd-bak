package com.cqut.czb.bn.service.petrolRecharge;

import com.cqut.czb.bn.entity.dto.TroubleshootingDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;

public interface IPetrolRechargeService {
    PageInfo<PetrolRechargeOutputDTO> getPetrolRechargeList(PetrolRechargeInputDTO inputDTO);

    boolean recharge(PetrolRechargeInputDTO record);

    boolean troubleshooting(TroubleshootingDTO inputDTO);

    Workbook exportRechargeRecords(PetrolRechargeInputDTO inputDTO) throws Exception;

    Workbook exportSaleRecords(GetPetrolSaleInfoInputDTO inputDTO) throws Exception;
}
