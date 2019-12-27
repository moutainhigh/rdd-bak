package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.AgentCenterCommonPartnerDto;

import java.util.List;
public interface AgentCenterCommonPartnerMapperExtra {
    List<AgentCenterCommonPartnerDto> list(BusinessCommonUserVo businessCommonUserVo);
}
