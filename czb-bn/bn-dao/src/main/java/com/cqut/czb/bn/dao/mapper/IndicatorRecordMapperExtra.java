package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.IndicatorRecord.IndicatorRecordDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.IndicatorRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndicatorRecordMapperExtra {

    List<IndicatorRecordDTO> getIndicatorRecordList(@Param("indicatorRecordDTO")IndicatorRecordDTO indicatorRecordDTO);

    /*
        批量确认达标
     */
    int ConfirmComplianceByState(List<IndicatorRecordDTO> list);

    int insertIndicatorRecordList(List<IndicatorRecordDTO> list);

    IndicatorRecord selectIndicatorRecordInfo(String userId);

    int statisticsPeople(PartnerDTO partnerDTO);
}
