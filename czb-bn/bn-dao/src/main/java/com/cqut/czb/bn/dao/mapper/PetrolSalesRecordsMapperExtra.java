package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.SaleInfoOutputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetrolSalesRecordsMapperExtra {
    List<SaleInfoOutputDTO> getPetrolSaleInfoList(GetPetrolSaleInfoInputDTO infoInputDTO);

    List<Petrol> getGTSoldPetrolForUser(String userId);

    List<PetrolSalesRecords> getPhysicalCardsForUser(@Param("userId") String userId, @Param("petrolKind")String petrolKind);
}