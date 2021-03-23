package com.cqut.czb.bn.service.impl.h5Stock;

import com.cqut.czb.bn.dao.mapper.h5Stock.H5StockMapper;
import com.cqut.czb.bn.entity.dto.H5CommodityDTO;
import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.h5Stock.H5StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Liyan
 */
@Service
public class H5StockServiceImpl implements H5StockService {

    @Autowired
    private H5StockMapper mapper;

    @Override
    public JSONResult<List<H5StockDTO>> h5StockList(H5StockDTO h5StockDTO) {
        return mapper.h5StockList(h5StockDTO);
    }

    @Override
    public JSONResult<List<H5CommodityDTO>> h5CommodityList(H5CommodityDTO commodityDTO) {
        return mapper.h5CommodityList(commodityDTO);
    }

    @Override
    public JSONResult<List<Double>> h5CommodityStockPriceGroup(String commodityId) {
        return mapper.h5CommodityStockPriceGroup(commodityId);
    }
}
