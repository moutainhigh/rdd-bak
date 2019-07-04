package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.CommodityUsageRecord;

public interface CommodityUsageRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(CommodityUsageRecord record);

    int insertSelective(CommodityUsageRecord record);

    CommodityUsageRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(CommodityUsageRecord record);

    int updateByPrimaryKey(CommodityUsageRecord record);
}