package com.cqut.czb.bn.service.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.Date;

public interface GeneralPartnerService {
    JSONResult getGeneralPartnerList(String userId, String account, Date creatAt, Integer areaId, PageDTO pageDTO);

    JSONResult getNumberOfDevelopment(String userId, Integer condition);

}
