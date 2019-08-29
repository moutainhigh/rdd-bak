package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.shopManagement.SettlementDTO;
import com.cqut.czb.bn.entity.dto.shopManagement.SettlementPageDTO;
import com.cqut.czb.bn.entity.dto.shopManagement.ShopManagementDTO;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-28 14:48
 */
public interface ShopManagementService {
    /**
     * 服务商管理列表获取
     */
    PageInfo getShopList(ShopManagementDTO shopManagementDTO);

    /**
     * 结算列表获取
     */
    SettlementPageDTO getSettlement(SettlementDTO settlementDTO);

    String settleOrder(HttpServletResponse response, HttpServletRequest request, SettlementDTO settlementDTO);

    Boolean auditShop(ShopManagementDTO shopManagementDTO);
}