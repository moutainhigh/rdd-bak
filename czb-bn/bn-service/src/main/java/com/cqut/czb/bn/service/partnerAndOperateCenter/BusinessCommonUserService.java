package com.cqut.czb.bn.service.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerAndOperateCenter.BusinessCommonUserOutputDTO;
import com.github.pagehelper.PageInfo;

import java.util.Date;

public interface BusinessCommonUserService {

    PageInfo<BusinessCommonUserOutputDTO> list(String userId, String mobile, Date createAt, String area, PageDTO pageDTO);
}
