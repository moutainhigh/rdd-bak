package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.H5StockDTO;

public interface H5PaymentBuyCommodityMapperExtra {
    String getStockId();

    int updateState(H5StockDTO h5StockDTO);

    int updateStockState(H5StockDTO h5StockDTO);
}
