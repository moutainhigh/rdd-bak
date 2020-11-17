package com.cqut.czb.bn.dao.mapper.withoutCard;

import com.cqut.czb.bn.entity.entity.withoutCard.CommodityWithoutCard;

public interface CommodityWithoutCardMapper {
    int deleteByPrimaryKey(String commodityId);

    int insert(CommodityWithoutCard record);

    int insertSelective(CommodityWithoutCard record);

    CommodityWithoutCard selectByPrimaryKey(String commodityId);

    int updateByPrimaryKeySelective(CommodityWithoutCard record);

    int updateByPrimaryKey(CommodityWithoutCard record);
}