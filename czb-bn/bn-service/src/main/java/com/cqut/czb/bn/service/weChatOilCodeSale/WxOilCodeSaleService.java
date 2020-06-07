package com.cqut.czb.bn.service.weChatOilCodeSale;

import com.cqut.czb.bn.entity.dto.CommodityStockDTO;
import com.cqut.czb.bn.entity.dto.WxStockDetailsDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface WxOilCodeSaleService {
    JSONResult getWxOilCodeSaleList(CommodityStockDTO commodityStockDTO);

    JSONResult getWxStockDetailsList(WxStockDetailsDTO wxStockDetailsDTO);

    JSONResult editWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO);

    JSONResult deleteWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO);
}
