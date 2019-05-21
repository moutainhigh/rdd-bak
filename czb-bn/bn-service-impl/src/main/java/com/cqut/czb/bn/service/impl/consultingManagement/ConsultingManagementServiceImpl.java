package com.cqut.czb.bn.service.impl.consultingManagement;

import com.cqut.czb.bn.dao.mapper.EnterpriseConsultingInfoMapperExtra;
import com.cqut.czb.bn.entity.dto.consultingManagement.ConsultingInputDTO;
import com.cqut.czb.bn.entity.dto.consultingManagement.HandleConsultationInputDTO;
import com.cqut.czb.bn.entity.entity.EnterpriseConsultingInfo;
import com.cqut.czb.bn.service.consultingManagement.IConsultingManagementService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ConsultingManagementServiceImpl implements IConsultingManagementService {
    @Autowired
    EnterpriseConsultingInfoMapperExtra enterpriseConsultingInfoMapperExtra;

    @Override
    public int handleConsultation(HandleConsultationInputDTO inputDTO) {
        if(inputDTO.getIds()!=null && inputDTO.getIds().length()>0){
            inputDTO.setIdArr(inputDTO.getIds().split(","));
        }

        return enterpriseConsultingInfoMapperExtra.handleConsultation(inputDTO.getIdArr());
    }

    @Override
    public PageInfo<EnterpriseConsultingInfo> getConsultingList(ConsultingInputDTO inputDTO) {
        PageHelper.startPage(inputDTO.getCurrentPage(),inputDTO.getPageSize(),true);
        return new PageInfo<>(enterpriseConsultingInfoMapperExtra.getConsultingInfoList(inputDTO));
    }
}
