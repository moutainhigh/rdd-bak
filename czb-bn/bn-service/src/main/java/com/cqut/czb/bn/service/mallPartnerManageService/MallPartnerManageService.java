package com.cqut.czb.bn.service.mallPartnerManageService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.MallPartnerDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.OrderDetails;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface MallPartnerManageService {
    JSONResult statisticsOrder();

    PageInfo<MallPartnerDTO> getMallPartnerList(MallPartnerDTO mallPartnerDTO, PageDTO pageDTO);

    List<MallPartnerDTO> getMallPartnerConsumptionDetails(MallPartnerDTO mallPartnerDTO);

    PageInfo<OrderDetails> getEveryOrderDetails(OrderDetails orderDetails, PageDTO pageDTO);

    JSONResult getEveryTotalMoney(MallPartnerDTO mallPartnerDTO);
}
