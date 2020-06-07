package com.cqut.czb.bn.service.impl.WxOilCodeSale;

import com.cqut.czb.bn.dao.mapper.WxOilCodeSaleMapperExtra;
import com.cqut.czb.bn.entity.dto.CommodityStockDTO;
import com.cqut.czb.bn.entity.dto.WxStockDetailsDTO;
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

    @Override
    public JSONResult getWxStockDetailsList(WxStockDetailsDTO wxStockDetailsDTO) {
        PageHelper.startPage(wxStockDetailsDTO.getCurrentPage(),wxStockDetailsDTO.getPageSize(),true);
        PageInfo pageInfo = new PageInfo();
        List<WxStockDetailsDTO> list = wxOilCodeSaleMapperExtra.getWxStockDetailsList(wxStockDetailsDTO);
        pageInfo.setList(list);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult editWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO) {
        if (wxOilCodeSaleMapperExtra.check(wxStockDetailsDTO)!=0){
            return new JSONResult("输入的电子码重复", 200, false);
        }else{
            boolean result = wxOilCodeSaleMapperExtra.editWxStockDetails(wxStockDetailsDTO)>0;
            return new JSONResult("修改", 200, result);
        }
    }

    @Override
    public JSONResult deleteWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO) {
        boolean result = wxOilCodeSaleMapperExtra.deleteWxStock(wxStockDetailsDTO)>0
                && wxOilCodeSaleMapperExtra.deleteWxStockAttr(wxStockDetailsDTO)>0
                && wxOilCodeSaleMapperExtra.updateWxCommodityNum(wxStockDetailsDTO)>0;
        return new JSONResult("删除", 200, result);
    }
}
