package com.cqut.czb.bn.service.partnerAndOperateCenter;


import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.AgentCenterBusinessPartnerDto;

import java.util.Date;
import java.util.List;

public interface AgentCenterBusinessPartnerService {

    List<AgentCenterBusinessPartnerDto> list(String mobile, Date createAt,Integer isVip,String superiorMobile, String area, PageDTO pageDTO);
}
