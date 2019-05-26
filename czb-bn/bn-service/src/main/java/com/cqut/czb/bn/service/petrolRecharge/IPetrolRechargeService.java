package com.cqut.czb.bn.service.petrolRecharge;

import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;

public interface IPetrolRechargeService {
    PageInfo<PetrolRechargeOutputDTO> getPetrolRechargeList(PetrolRechargeInputDTO inputDTO);

    boolean recharge(String record);

    Workbook exportRechargeRecords(PetrolRechargeInputDTO inputDTO) throws Exception;
}
