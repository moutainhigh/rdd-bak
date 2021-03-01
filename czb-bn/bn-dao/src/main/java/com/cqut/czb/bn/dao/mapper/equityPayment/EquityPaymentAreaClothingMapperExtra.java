package com.cqut.czb.bn.dao.mapper.equityPayment;

import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentAreaClothingDTO;

import java.util.List;

public interface EquityPaymentAreaClothingMapperExtra {
    List<EquityPaymentAreaClothingDTO> getDistrictServiceInformation(String productCode);

    List<EquityPaymentAreaClothingDTO> getAreaClothingList(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO);

    int insertAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO);

    int updateAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO);

    int deleteAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO);
}
