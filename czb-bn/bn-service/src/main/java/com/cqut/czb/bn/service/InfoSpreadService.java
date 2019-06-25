package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

public interface InfoSpreadService {

     PartnerDTO getPartnerInfo(PartnerDTO partnerDTO,User user);

     List<PartnerDTO> getPartnerChildInfo(PartnerDTO partnerDTO);

     PageInfo<PartnerDTO> getNextChildInfo(PartnerDTO partnerDTO, PageDTO pageDTO);

     ArrayList getNewChildByDay(PartnerDTO partnerDTO, User user);

     PartnerDTO getTotalInfo(PartnerDTO partnerDTO,User user);

     PartnerDTO selectAllPartnerInfo(PartnerDTO partnerDTO);

     public Boolean addChildPromotion(PartnerDTO partnerDTO);

     public Boolean addChildConsumer(PartnerDTO partnerDTO);

     PageInfo<PartnerDTO> getChildByName(PartnerDTO partnerDTO,PageDTO pageDTO);
 }
