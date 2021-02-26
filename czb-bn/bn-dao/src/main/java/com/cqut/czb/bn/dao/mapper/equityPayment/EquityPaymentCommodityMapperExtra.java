package com.cqut.czb.bn.dao.mapper.equityPayment;

import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentCommodityDTO;
import com.cqut.czb.bn.entity.dto.integral.CommodityDetailsDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.List;

public interface EquityPaymentCommodityMapperExtra {
    /**
     * 用于后台管理系统
     * @return
     */
    List<CommodityDetailsDTO> selectAllCommodityTitle();

    EquityPaymentCommodityDTO selectCommodityByGoodsId(String goodsId);

    List<EquityPaymentCommodityDTO> getCommodityList(EquityPaymentCommodityDTO equityPaymentCommodityDTO);

    int insertEquityPayment(EquityPaymentCommodityDTO equityPaymentCommodityDTO);
}
