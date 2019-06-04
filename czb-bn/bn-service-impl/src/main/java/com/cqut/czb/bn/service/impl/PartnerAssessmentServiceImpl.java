package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.IndicatorRecordMapperExtra;
import com.cqut.czb.bn.entity.dto.IndicatorRecord.IndicatorRecordDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PartnerAssessmentServiceImpl implements com.cqut.czb.bn.service.IndicatorRecordService {
    @Autowired
    IndicatorRecordMapperExtra indicatorRecordMapperExtra;

    @Override
    public PageInfo<IndicatorRecordDTO> getIndicatorRecordList(IndicatorRecordDTO indicatorRecordDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<IndicatorRecordDTO> list = indicatorRecordMapperExtra.getIndicatorRecordList(indicatorRecordDTO);
        return new PageInfo<>(list);
    }

    @Override
    public Boolean ConfirmCompliance(List<String> recordIds) {
        return null;
    }
}
