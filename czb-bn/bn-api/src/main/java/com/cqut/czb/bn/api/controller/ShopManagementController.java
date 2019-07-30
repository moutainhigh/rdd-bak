package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.shopManagement.SettlementDTO;
import com.cqut.czb.bn.entity.dto.shopManagement.ShopManagementDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.ShopManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-28 14:46
 */
@RestController
@RequestMapping("/api/shopManagement")
public class ShopManagementController {

    @Autowired
    private ShopManagementService shopManagementService;

    @GetMapping("/getShopList")
    public JSONResult getShopList(ShopManagementDTO shopManagementDTO){
        return new JSONResult(shopManagementService.getShopList(shopManagementDTO));
    }

    @GetMapping("/getSettlement")
    public JSONResult getSettlement(@Validated SettlementDTO settlementDTO){
        return new JSONResult(shopManagementService.getSettlement(settlementDTO));
    }

    @PostMapping("/settleOrder")
    public JSONResult settleOrder(HttpServletResponse response, HttpServletRequest request, @Validated SettlementDTO settlementDTO){
        return new JSONResult(shopManagementService.settleOrder(response, request, settlementDTO));
    }
}