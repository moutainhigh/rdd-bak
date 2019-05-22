package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.consultingManagement.ConsultingInputDTO;
import com.cqut.czb.bn.entity.dto.consultingManagement.HandleConsultationInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.consultingManagement.IConsultingManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultingManagement")
public class EnterpriseConsultingManagementController {
    @Autowired
    IConsultingManagementService consultingManagementService;


    @RequestMapping(value = "/getConsultingInfoList",method = RequestMethod.GET)
    public JSONResult getConsultingInfoList( ConsultingInputDTO inputDTO){
        return  new JSONResult(consultingManagementService.getConsultingList(inputDTO));
    }

    @RequestMapping(value = "/handleConsultation",method = RequestMethod.POST)
    public JSONResult handleConsultation(@RequestBody HandleConsultationInputDTO inputDTO){
        return new JSONResult(consultingManagementService.handleConsultation(inputDTO));
    }

    @RequestMapping(value ="/insertConsultation",method = RequestMethod.POST)
    public JSONResult insertConsultation(@RequestBody ConsultingInputDTO inputDTO){
        return new JSONResult(consultingManagementService.insertConsultation(inputDTO));
    }
}
