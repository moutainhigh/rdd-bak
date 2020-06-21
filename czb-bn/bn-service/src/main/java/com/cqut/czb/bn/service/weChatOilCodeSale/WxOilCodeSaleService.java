package com.cqut.czb.bn.service.weChatOilCodeSale;

import com.cqut.czb.bn.entity.dto.CommodityStockDTO;
import com.cqut.czb.bn.entity.dto.WxStockDetailsDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

public interface WxOilCodeSaleService {
    JSONResult getWxOilCodeSaleList(CommodityStockDTO commodityStockDTO);

    JSONResult getWxStockDetailsList(WxStockDetailsDTO wxStockDetailsDTO);

    JSONResult editWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO);

    JSONResult deleteWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO);

    JSONResult importDate(MultipartFile file) throws Exception;

    Workbook exportDate(WxStockDetailsDTO wxStockDetailsDTO) throws Exception;
}
