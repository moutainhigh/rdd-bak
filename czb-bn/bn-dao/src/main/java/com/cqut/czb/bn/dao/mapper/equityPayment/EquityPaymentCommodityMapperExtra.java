package com.cqut.czb.bn.dao.mapper.equityPayment;

import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentCommodityDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentTypeDTO;
import com.cqut.czb.bn.entity.dto.integral.CommodityDetailsDTO;
import org.apache.ibatis.annotations.Param;
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

    int deleteCommodity(@Param("goodsId") String goodsId, @Param("isDelete") boolean isDelete);

    int insertEquityPayment(EquityPaymentCommodityDTO equityPaymentCommodityDTO);

    int updateEquityPayment(EquityPaymentCommodityDTO equityPaymentCommodityDTO);

    int getCountOfCommodityByType(EquityPaymentTypeDTO equityPaymentTypeDTO);
}
