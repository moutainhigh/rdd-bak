package com.cqut.czb.bn.service.h5Stock;

import com.cqut.czb.bn.entity.dto.H5CommodityDTO;
import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface H5StockService {

    JSONResult<List<H5StockDTO>> h5StockList(H5StockDTO h5StockDTO);

    JSONResult<List<H5CommodityDTO>> h5CommodityList(H5CommodityDTO commodityDTO);

    List<Double> h5CommodityStockPriceGroup(String commodityId);

    int h5ImportData(MultipartFile file, Integer recordType) throws Exception;

    List<H5StockDTO> getCommodity();

    JSONResult getTotalConsumption(H5StockDTO h5StockDTO);
}
