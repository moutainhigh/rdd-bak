package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.IndicatorRecord.IndicatorRecordDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IndicatorRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 合伙人考核管理
 * 徐皓东
 * 更新日期: 2019/06/03
 */
@RestController
@RequestMapping("/api/partnerAssessment")
public class PartnerAssessmentController {
    @Autowired
    IndicatorRecordService indicatorRecordService;

    @RequestMapping(value = "/getIndicatorRecordList",method = RequestMethod.GET)
    public JSONResult getIndicatorRecordList(IndicatorRecordDTO input, PageDTO pageDTO){
        return new JSONResult(indicatorRecordService.getIndicatorRecordList(input,pageDTO));
    }
}
