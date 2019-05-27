package com.cqut.czb.bn.service.consultingManagement;

import com.cqut.czb.bn.entity.dto.consultingManagement.ConsultingInputDTO;
import com.cqut.czb.bn.entity.dto.consultingManagement.HandleConsultationInputDTO;
import com.cqut.czb.bn.entity.entity.EnterpriseConsultingInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IConsultingManagementService {
    PageInfo<EnterpriseConsultingInfo> getConsultingList(ConsultingInputDTO inputDTO);

    int handleConsultation(HandleConsultationInputDTO inputDTO);

    int insertConsultation(ConsultingInputDTO inputDTO);

    EnterpriseConsultingInfo getLastConsultation(String userId);
}
