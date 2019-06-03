package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.ConsumptionRecord;

public interface ConsumptionRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(ConsumptionRecord record);

    int insertSelective(ConsumptionRecord record);

    ConsumptionRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(ConsumptionRecord record);

    int updateByPrimaryKey(ConsumptionRecord record);
}