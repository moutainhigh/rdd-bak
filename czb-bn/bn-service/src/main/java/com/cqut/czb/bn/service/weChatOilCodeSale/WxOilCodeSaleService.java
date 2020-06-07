package com.cqut.czb.bn.service.weChatOilCodeSale;

import com.cqut.czb.bn.entity.dto.CommodityStockDTO;
import com.cqut.czb.bn.entity.dto.WxStockDetailsDTO;
import com.cqut.czb.bn.entity.entity.weChatOilSale.WXStock;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface WxOilCodeSaleService {
    JSONResult getWxOilCodeSaleList(CommodityStockDTO commodityStockDTO);

    JSONResult getWxStockDetailsList(WxStockDetailsDTO wxStockDetailsDTO);

    JSONResult editWxStockDetailsList(WxStockDetailsDTO wxStockDetailsDTO);
}
