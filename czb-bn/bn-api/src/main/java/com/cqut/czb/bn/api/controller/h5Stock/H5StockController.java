package com.cqut.czb.bn.api.controller.h5Stock;

import com.alibaba.fastjson.JSON;
import com.cqut.czb.bn.entity.dto.H5CommodityDTO;
import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.h5Stock.H5StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * @author Liyan
 */

@RequestMapping("/api/h5Stock")
@RestController
public class H5StockController {

    @Autowired
    private H5StockService stockService;

    @GetMapping("/h5StockList")
    public JSONResult<List<H5StockDTO>> h5StockList(H5StockDTO h5StockDTO){
        return stockService.h5StockList(h5StockDTO);
    }

    @GetMapping("/h5CommodityList")
    public JSONResult<List<H5CommodityDTO>> h5CommodityList(H5CommodityDTO commodityDTO){
        return stockService.h5CommodityList(commodityDTO);
    }

    @GetMapping("/priceGroup")
    public JSONResult<List<Double>> h5CommodityStockPriceGroup(String commodityId){
        return new JSONResult<>(stockService.h5CommodityStockPriceGroup(commodityId));
    }

    @PostMapping("/importData")
    public JSONResult h5ImportData(MultipartFile file, Integer recordType) {
        try {
            stockService.h5ImportData(file, recordType);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(500, "failed");
        }
        return new JSONResult(200, "success");
    }

    @GetMapping("/getCommodity")
    public JSONResult getCommodity(){
        return new JSONResult(stockService.getCommodity());
    }

    @GetMapping("/getTotalConsumption")
    public JSONResult getTotalConsumption(H5StockDTO h5StockDTO) {
        return stockService.getTotalConsumption(h5StockDTO);
    }

    @PostMapping("/dropCommodityById")
    public JSONResult dropCommodityById(String commodityId){
        if (stockService.dropCommodityById(commodityId)){
            return new JSONResult(200, "success");
        } else {
            return new JSONResult(500, "failed");
        }
    }
}
