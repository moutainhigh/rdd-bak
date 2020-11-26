package com.cqut.czb.bn.dao.mapper.withoutCard;


import com.cqut.czb.bn.entity.dto.withoutCard.PetrolSalesWithoutDto;

import java.util.List;

public interface WithoutCardSellManagementMapperExtra {

    List<PetrolSalesWithoutDto> listPetrolSellManagement(PetrolSalesWithoutDto petrolSalesWithoutDto);

    String getPetrolSellManagementTotal(PetrolSalesWithoutDto petrolSalesWithoutDto);
}
