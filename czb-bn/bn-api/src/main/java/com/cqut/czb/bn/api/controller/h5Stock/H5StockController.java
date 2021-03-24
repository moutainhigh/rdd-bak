package com.cqut.czb.bn.api.controller.h5Stock;

import com.cqut.czb.bn.entity.dto.H5CommodityDTO;
import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.h5Stock.H5StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getCommodity")
    public JSONResult getCommodity(){
        return new JSONResult(stockService.getCommodity());
    }
}
