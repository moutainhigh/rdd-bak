package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface PartnerConsumptionService {
    PageInfo<PartnerDTO> getConsumptionList(PartnerDTO partnerDTO, PageDTO pageDTO);

    PageInfo<PartnerDTO> getDetailConsumption(PartnerDTO partnerDTO,PageDTO pageDTO);
}
