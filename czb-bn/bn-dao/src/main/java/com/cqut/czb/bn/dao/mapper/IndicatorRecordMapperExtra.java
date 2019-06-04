package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.IndicatorRecord.IndicatorRecordDTO;

import java.util.List;

public interface IndicatorRecordMapperExtra {
    List<IndicatorRecordDTO> getIndicatorRecordList(IndicatorRecordDTO indicatorRecordDTO);
}
