package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.CommodityStockDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WxOilCodeSaleMapperExtra {
    List<CommodityStockDTO> getWxOilCodeSaleList(CommodityStockDTO commodityStockDTO);
}
