package com.cqut.czb.bn.dao.mapper.weChatOilCodeSale;


import com.cqut.czb.bn.entity.entity.weChatOilSale.WXStockAttr;

public interface WXStockAttrMapper {
    int deleteByPrimaryKey(String stockAttrId);

    int insert(WXStockAttr record);

    int insertSelective(WXStockAttr record);

    WXStockAttr selectByPrimaryKey(String stockAttrId);

    int updateByPrimaryKeySelective(WXStockAttr record);

    int updateByPrimaryKey(WXStockAttr record);
}