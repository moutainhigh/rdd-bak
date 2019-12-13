package com.cqut.czb.bn.service.impl.partnerAndOperateCenter;

import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.AgentCenterBusinessPartnerMapperExtra;
import com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter.BusinessCommonUserVo;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.AgentCenterBusinessPartnerDto;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.BusinessCommonUserOutputDTO;
import com.cqut.czb.bn.service.partnerAndOperateCenter.AgentCenterBusinessPartnerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AgentCenterBusinessPartnerServiceImpl implements AgentCenterBusinessPartnerService {

    @Autowired
    private AgentCenterBusinessPartnerMapperExtra agentCenterBusinessPartnerMapperExtra;



    @Override
    public List<AgentCenterBusinessPartnerDto> list(String mobile, Date createAt, String area, PageDTO pageDTO) {
        if(mobile != null ){
            mobile = mobile.trim();
        }
        if(area!=null){
            area = area.trim();
        }
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize(),true);
        BusinessCommonUserVo businessCommonUserVo = new BusinessCommonUserVo(mobile,createAt,area);
        List<AgentCenterBusinessPartnerDto> list = agentCenterBusinessPartnerMapperExtra.list(businessCommonUserVo);
        return list;
    }
}
