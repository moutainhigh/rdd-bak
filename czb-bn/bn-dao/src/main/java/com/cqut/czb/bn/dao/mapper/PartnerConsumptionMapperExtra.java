package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;

import java.util.List;

public interface PartnerConsumptionMapperExtra {
    List<PartnerDTO> selectConsumptionList(PartnerDTO partnerDTO);

    List<PartnerDTO> selectConsumptionCount(PartnerDTO partnerDTO);

    List<PartnerDTO> selectDetailConsumption(PartnerDTO partnerDTO);
}
