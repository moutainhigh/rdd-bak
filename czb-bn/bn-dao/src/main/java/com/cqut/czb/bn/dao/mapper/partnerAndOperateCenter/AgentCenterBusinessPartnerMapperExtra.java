package com.cqut.czb.bn.dao.mapper.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.AgentCenterBusinessPartnerDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface AgentCenterBusinessPartnerMapperExtra {
    List<AgentCenterBusinessPartnerDto> list(BusinessCommonUserVo businessCommonUserVo);
}
