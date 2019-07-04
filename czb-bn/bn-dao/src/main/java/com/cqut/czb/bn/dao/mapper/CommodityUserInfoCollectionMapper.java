package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.CommodityUserInfoCollection;

public interface CommodityUserInfoCollectionMapper {
    int deleteByPrimaryKey(String infoId);

    int insert(CommodityUserInfoCollection record);

    int insertSelective(CommodityUserInfoCollection record);

    CommodityUserInfoCollection selectByPrimaryKey(String infoId);

    int updateByPrimaryKeySelective(CommodityUserInfoCollection record);

    int updateByPrimaryKey(CommodityUserInfoCollection record);
}