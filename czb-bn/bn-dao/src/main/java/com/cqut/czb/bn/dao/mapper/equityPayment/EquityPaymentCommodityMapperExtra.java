package com.cqut.czb.bn.dao.mapper.equityPayment;

import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentCommodityDTO;

import java.util.List;

public interface EquityPaymentCommodityMapperExtra {
    /**
     * 用于后台管理系统
     * @return
     */
    List<EquityPaymentCommodityDTO> selectAllCommodityTitle();
}