package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.IndicatorRecord.IndicatorRecordDTO;
import com.cqut.czb.bn.entity.entity.IndicatorRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndicatorRecordMapperExtra {

    List<IndicatorRecordDTO> getIndicatorRecordList(IndicatorRecordDTO indicatorRecordDTO);

    int ConfirmComplianceByState(@Param("recordIds")String recordIds);

    IndicatorRecord selectIndicatorRecordInfo(String userId);
}
