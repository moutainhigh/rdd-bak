package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.consultingManagement.ConsultingInputDTO;
import com.cqut.czb.bn.entity.entity.EnterpriseConsultingInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnterpriseConsultingInfoMapperExtra {
    List<EnterpriseConsultingInfo> getConsultingInfoList(ConsultingInputDTO inputDTO);

    int handleConsultation(@Param("list") String[] list);

    EnterpriseConsultingInfo getLastConsultation(@Param("userId") String userId);

}