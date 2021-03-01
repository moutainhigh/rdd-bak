package com.cqut.czb.bn.dao.mapper.equityPayment;

import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentAreaClothingDTO;

import java.util.List;

public interface EquityPaymentAreaClothingMapperExtra {
    List<EquityPaymentAreaClothingDTO> getDistrictServiceInformation(String productCode);
}
