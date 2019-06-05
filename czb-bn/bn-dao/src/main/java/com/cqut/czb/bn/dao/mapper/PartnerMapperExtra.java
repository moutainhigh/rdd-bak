package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;

import java.util.List;

public interface PartnerMapperExtra {
     List<PartnerDTO> selectPartnerChildInfo(PartnerDTO partnerDTO);

     PartnerDTO selectPartnerInfo(PartnerDTO partnerDTO);

     PartnerDTO selectHistoryInfo(PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildInfoWithTime(PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildInfoWithDay(PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildInfoWithMoney(PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildWithDayMoney(PartnerDTO partnerDTO);

    List<PartnerDTO> selectNextChild(PartnerDTO partnerDTO);
}
