package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.User;

import java.util.List;

public interface InfoSpreadService {

     PartnerDTO getPartnerInfo(PartnerDTO partnerDTO,User user);

     List<PartnerDTO> getPartnerChildInfo(PartnerDTO partnerDTO);

     List<PartnerDTO> getNextChildInfo(PartnerDTO partnerDTO);

     List<PartnerDTO> getNewChildByDay(PartnerDTO partnerDTO,User user);

     PartnerDTO getTotalInfo(PartnerDTO partnerDTO,User user);
 }
