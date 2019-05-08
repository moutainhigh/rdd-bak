package com.cqut.czb.bn.service.petrolRecharge;

import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeOutputDTO;
import com.github.pagehelper.PageInfo;

public interface IPetrolRechargeService {
    PageInfo<PetrolRechargeOutputDTO> getPetrolRechargeList(PetrolRechargeInputDTO inputDTO);
}
