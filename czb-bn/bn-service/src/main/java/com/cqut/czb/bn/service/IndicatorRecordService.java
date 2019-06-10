package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.IndicatorRecord.IndicatorRecordDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创建人：徐皓东
 * 创建时间：2019/06/03
 * 作用：合伙人考核管理
 */
public interface IndicatorRecordService {
    PageInfo<IndicatorRecordDTO> getIndicatorRecordList(IndicatorRecordDTO indicatorRecordDTO, PageDTO pageDTO);

    Boolean ConfirmComplianceByState(String recordIds);

    String exportExaminationRecords(HttpServletResponse response, HttpServletRequest request,IndicatorRecordDTO input);

    /**
     * 统计两个实际人数
     */
    Boolean statisticsPeople(String userId);
}
