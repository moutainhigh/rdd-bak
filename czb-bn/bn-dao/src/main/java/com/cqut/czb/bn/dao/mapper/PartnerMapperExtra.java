package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartnerMapperExtra {
     List<PartnerDTO> selectPartnerChildInfo(PartnerDTO partnerDTO);

     PartnerDTO selectPartnerInfo(PartnerDTO partnerDTO);

    PartnerDTO selectPartner(PartnerDTO partnerDTO);

     PartnerDTO selectHistoryInfo(PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildInfoWithTime(@Param("list")List<PartnerDTO> children ,@Param("partner") PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildInfoWithDay(@Param("list")List<PartnerDTO> children ,@Param("partner") PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildInfoWithMoney(@Param("list")List<PartnerDTO> children ,@Param("partner") PartnerDTO partnerDTO);

    List<PartnerDTO> selectPartnerChildWithDayMoney(@Param("list")List<PartnerDTO> children ,@Param("partner") PartnerDTO partnerDTO);

    List<PartnerDTO> selectNextChild(PartnerDTO partnerDTO);
}
