package com.cqut.czb.bn.service.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.GeneralPartnerUserPageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.partnerAndOperateCenter.GetGeneralPartnerListVo;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.Date;

public interface GeneralPartnerService {
    JSONResult getGeneralPartnerList(GeneralPartnerUserPageDTO pageDTO);

    JSONResult getNumberOfDevelopment(String userId, Integer condition);

}
