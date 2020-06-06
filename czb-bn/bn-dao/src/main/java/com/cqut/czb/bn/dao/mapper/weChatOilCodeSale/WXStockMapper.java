package com.cqut.czb.bn.dao.mapper.weChatOilCodeSale;

import com.cqut.czb.bn.entity.entity.weChatOilSale.WXStock;

public interface WXStockMapper {
    int deleteByPrimaryKey(String stockId);

    int insert(WXStock record);

    int insertSelective(WXStock record);

    WXStock selectByPrimaryKey(String stockId);

    int updateByPrimaryKeySelective(WXStock record);

    int updateByPrimaryKey(WXStock record);
}