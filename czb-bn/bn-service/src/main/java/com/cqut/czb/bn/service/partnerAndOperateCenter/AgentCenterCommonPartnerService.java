package com.cqut.czb.bn.service.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.AgentCenterCommonPartnerDto;

import java.util.Date;
import java.util.List;


public interface AgentCenterCommonPartnerService {

    List<AgentCenterCommonPartnerDto> list(String mobile, Date createAt, String area, String spreadAccount, Integer isVip, PageDTO pageDTO);
}
