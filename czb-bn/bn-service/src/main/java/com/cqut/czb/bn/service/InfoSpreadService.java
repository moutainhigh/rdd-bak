package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;

import java.util.List;

public interface InfoSpreadService {

     PartnerDTO getPartnerInfo(PartnerDTO partnerDTO);

     List<PartnerDTO> getPartnerChildInfo(PartnerDTO partnerDTO);

     List<PartnerDTO> getNextChildInfo(PartnerDTO partnerDTO);

     List<PartnerDTO> getNewChildByDay(PartnerDTO partnerDTO);

     PartnerDTO getTotalInfo(PartnerDTO partnerDTO);
 }
