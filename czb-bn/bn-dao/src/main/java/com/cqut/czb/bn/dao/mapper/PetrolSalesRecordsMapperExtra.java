package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;

import java.util.List;

public interface PetrolSalesRecordsMapperExtra {
    List<SaleInfoOutputDTO> getPetrolSaleInfoList(GetPetrolSaleInfoInputDTO infoInputDTO);
}