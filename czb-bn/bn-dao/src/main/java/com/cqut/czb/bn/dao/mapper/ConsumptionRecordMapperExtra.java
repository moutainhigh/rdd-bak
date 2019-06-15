package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.ConsumptionRecord;

import java.util.List;

public interface ConsumptionRecordMapperExtra {
    int deleteByPrimaryKey(String recordId);

    int insert(ConsumptionRecord record);

    int insertSelective(ConsumptionRecord record);

    List<ConsumptionRecord> selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(ConsumptionRecord record);

    int updateByPrimaryKey(ConsumptionRecord record);
}