package com.cqut.czb.bn.service.impl.partnerAndOperateCenter;

import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.AgentCenterCommonPartnerMapperExtra;
import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.BusinessCommonUserVo;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.AgentCenterBusinessPartnerDto;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.AgentCenterCommonPartnerDto;
import com.cqut.czb.bn.service.partnerAndOperateCenter.AgentCenterBusinessPartnerService;
import com.cqut.czb.bn.service.partnerAndOperateCenter.AgentCenterCommonPartnerService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AgentCenterCommonPartnerServiceImpl implements AgentCenterCommonPartnerService {

    @Autowired
    private AgentCenterCommonPartnerMapperExtra agentCenterCommonPartnerMapperExtra;

    @Override
    public List<AgentCenterCommonPartnerDto> list(String mobile, Date createAt, String area, PageDTO pageDTO) {
        if(mobile != null ){
            mobile = mobile.trim();
        }
        if(area!=null){
            area = area.trim();
        }
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize(),true);
        BusinessCommonUserVo businessCommonUserVo = new BusinessCommonUserVo(mobile,createAt,area);
        List<AgentCenterCommonPartnerDto> list = agentCenterCommonPartnerMapperExtra.list(businessCommonUserVo);
        return list;
    }
}
