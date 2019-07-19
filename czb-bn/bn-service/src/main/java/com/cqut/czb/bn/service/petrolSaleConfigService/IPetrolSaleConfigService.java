package com.cqut.czb.bn.service.petrolSaleConfigService;

import com.cqut.czb.bn.entity.dto.petrolSaleConfig.PetrolSaleConfigOutputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleConfig.SetAreaConfigInputDTO;
import com.cqut.czb.bn.entity.entity.PetrolSaleConfig;

import java.util.List;


public interface IPetrolSaleConfigService {
    List<PetrolSaleConfigOutputDTO> getPetrolSaleConfigs(String area);

    boolean updatePetrolSaleConfig(PetrolSaleConfig petrolSaleConfig);

    int setAreaConfig(SetAreaConfigInputDTO inputDTO);
}
