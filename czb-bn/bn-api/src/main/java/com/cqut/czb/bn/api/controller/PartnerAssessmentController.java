package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.IndicatorRecord.IndicatorRecordDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IndicatorRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * 合伙人考核管理
 * 徐皓东
 * 更新日期: 2019/06/03
 */
@RestController
@Validated
@RequestMapping("/api/partnerAssessment")
public class PartnerAssessmentController {

    @Autowired
    IndicatorRecordService indicatorRecordService;

    @RequestMapping(value = "/getIndicatorRecordList",method = RequestMethod.GET)
    public JSONResult getIndicatorRecordList(IndicatorRecordDTO input, PageDTO pageDTO){
        return new JSONResult(indicatorRecordService.getIndicatorRecordList(input,pageDTO));
    }

    @RequestMapping(value = "/comfirmCompliance",method = RequestMethod.POST)
    public JSONResult comfirmComplianceByRecordIds(@NotNull(message = "确认ID不能为空")String recordIds){
        return new JSONResult(indicatorRecordService.ConfirmComplianceByState(recordIds));
    }

    @PostMapping(value = "/exportExaminationRecords")
    public JSONResult exportExaminationRecords(HttpServletResponse response, HttpServletRequest request,IndicatorRecordDTO input){
        return new JSONResult(indicatorRecordService.exportExaminationRecords(response,request,input));
    }

    @GetMapping(value = "/statistics")
    public JSONResult statisticsPeople(PartnerDTO partnerDTO){
        return new JSONResult(indicatorRecordService.statisticsPeople(partnerDTO));
    }
}
