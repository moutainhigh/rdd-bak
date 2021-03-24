package com.cqut.czb.bn.service.mallPartnerManageService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.MallPartnerDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

public interface MallPartnerManageService {
    JSONResult statisticsMoney();

    PageInfo<MallPartnerDTO> getMallPartnerList(MallPartnerDTO mallPartnerDTO, PageDTO pageDTO);

    JSONResult getMallPartnerConsumptionDetails(User user);
}
