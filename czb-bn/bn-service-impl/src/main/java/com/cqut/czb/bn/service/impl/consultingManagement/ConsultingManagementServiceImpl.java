package com.cqut.czb.bn.service.impl.consultingManagement;

import com.cqut.czb.bn.dao.mapper.EnterpriseConsultingInfoMapper;
import com.cqut.czb.bn.dao.mapper.EnterpriseConsultingInfoMapperExtra;
import com.cqut.czb.bn.entity.dto.consultingManagement.ConsultingInputDTO;
import com.cqut.czb.bn.entity.dto.consultingManagement.HandleConsultationInputDTO;
import com.cqut.czb.bn.entity.entity.EnterpriseConsultingInfo;
import com.cqut.czb.bn.service.consultingManagement.IConsultingManagementService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class ConsultingManagementServiceImpl implements IConsultingManagementService {
    @Autowired
    EnterpriseConsultingInfoMapperExtra enterpriseConsultingInfoMapperExtra;

    @Autowired
    EnterpriseConsultingInfoMapper enterpriseConsultingInfoMapper;

    @Override
    public int handleConsultation(HandleConsultationInputDTO inputDTO) {
        if(inputDTO.getIds()!=null && inputDTO.getIds().length()>0){
            inputDTO.setIdArr(inputDTO.getIds().split(","));
        }

        return enterpriseConsultingInfoMapperExtra.handleConsultation(inputDTO.getIdArr());
    }

    @Override
    public EnterpriseConsultingInfo getLastConsultation(String userId) {
        return enterpriseConsultingInfoMapperExtra.getLastConsultation(userId);
    }

    @Override
    public int insertConsultation(ConsultingInputDTO inputDTO) {
        EnterpriseConsultingInfo enterpriseConsultingInfo = new EnterpriseConsultingInfo();
        enterpriseConsultingInfo.setConsultingId(StringUtil.createId());
        enterpriseConsultingInfo.setContactPhone(inputDTO.getContactPhone());
        enterpriseConsultingInfo.setEnterpriseName(inputDTO.getEnterpriseName());
        enterpriseConsultingInfo.setIsHandled(0);
        enterpriseConsultingInfo.setCreateAt(new Date());
        enterpriseConsultingInfo.setApplicantId(inputDTO.getApplicantId());
        enterpriseConsultingInfo.setApplicantAccount(inputDTO.getApplicantAccount());
        return enterpriseConsultingInfoMapper.insert(enterpriseConsultingInfo);
    }

    @Override
    public PageInfo<EnterpriseConsultingInfo> getConsultingList(ConsultingInputDTO inputDTO) {
        PageHelper.startPage(inputDTO.getCurrentPage(),inputDTO.getPageSize(),true);
        return new PageInfo<>(enterpriseConsultingInfoMapperExtra.getConsultingInfoList(inputDTO));
    }
}
