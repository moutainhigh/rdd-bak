package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.IndicatorRecord;

public interface IndicatorRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(IndicatorRecord record);

    int insertSelective(IndicatorRecord record);

    IndicatorRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(IndicatorRecord record);

    int updateByPrimaryKey(IndicatorRecord record);
}