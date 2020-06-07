package com.cqut.czb.bn.service.impl.WxOilCodeSale;

import com.cqut.czb.bn.dao.mapper.WxOilCodeSaleMapperExtra;
import com.cqut.czb.bn.entity.dto.CommodityStockDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatOilCodeSale.WxOilCodeSaleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxOilCodeSaleServiceImpl implements WxOilCodeSaleService {

    @Autowired
    WxOilCodeSaleMapperExtra wxOilCodeSaleMapperExtra;

    @Override
    public JSONResult getWxOilCodeSaleList(CommodityStockDTO commodityStockDTO) {
        PageHelper.startPage(commodityStockDTO.getCurrentPage(), commodityStockDTO.getPageSize(),true);
        PageInfo pageInfo = new PageInfo();
        List<CommodityStockDTO> list = wxOilCodeSaleMapperExtra.getWxOilCodeSaleList(commodityStockDTO);
        pageInfo.setList(list);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }
}
