package com.cqut.czb.bn.api.controller.weChatOilCodeSale;


import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.bn.entity.dto.CommodityStockDTO;

import com.cqut.czb.bn.entity.dto.WxStockDetailsDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatOilCodeSale.WxOilCodeSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/WxOilCodeSale")
public class WxOilCodeSaleController {
    @Autowired
    WxOilCodeSaleService wxOilCodeSaleService;
    /**
     * 获取表格数据
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getWxOilCodeSaleList",method = RequestMethod.POST)
    public JSONResult getWxOilCodeSaleList(CommodityStockDTO commodityStockDTO){
        return wxOilCodeSaleService.getWxOilCodeSaleList(commodityStockDTO);
    }
    /**
     * 获取详情页面数据
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getWxStockDetailsList",method = RequestMethod.POST)
    public JSONResult getWxStockDetailsList(WxStockDetailsDTO wxStockDetailsDTO){
        return wxOilCodeSaleService.getWxStockDetailsList(wxStockDetailsDTO);
    }
    /**
     * 修改
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/editWxStockDetails",method = RequestMethod.POST)
    public JSONResult editWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO){
        return wxOilCodeSaleService.editWxStockDetails(wxStockDetailsDTO);
    }

    /**
     * 删除
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/deleteWxStockDetails",method = RequestMethod.POST)
    public JSONResult deleteWxStockDetails(WxStockDetailsDTO wxStockDetailsDTO){
        return wxOilCodeSaleService.deleteWxStockDetails(wxStockDetailsDTO);
    }
}
