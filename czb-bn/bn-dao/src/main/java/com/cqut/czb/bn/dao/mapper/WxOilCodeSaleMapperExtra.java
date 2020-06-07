package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.CommodityStockDTO;
import com.cqut.czb.bn.entity.dto.WxStockDetailsDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxOilCodeSaleMapperExtra {
    List<CommodityStockDTO> getWxOilCodeSaleList(CommodityStockDTO commodityStockDTO);

    List<WxStockDetailsDTO> getWxStockDetailsList(WxStockDetailsDTO wxStockDetailsDTO);

    int editWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO);

    int check(WxStockDetailsDTO wxStockDetailsDTO);

    int deleteWxStock(WxStockDetailsDTO wxStockDetailsDTO);

    int deleteWxStockAttr(WxStockDetailsDTO wxStockDetailsDTO);

    int updateWxCommodityNum(WxStockDetailsDTO wxStockDetailsDTO);
}
