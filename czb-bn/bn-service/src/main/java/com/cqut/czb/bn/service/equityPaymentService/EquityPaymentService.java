package com.cqut.czb.bn.service.equityPaymentService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/25
 */
public interface EquityPaymentService {
    /**
     * 获取商品订单详情
     * @return
     */
    PageInfo<EquityPaymentDTO> getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO, PageDTO pageDTO);
}
