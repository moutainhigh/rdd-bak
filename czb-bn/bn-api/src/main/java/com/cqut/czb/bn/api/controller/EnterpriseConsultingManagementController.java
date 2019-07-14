package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.consultingManagement.ConsultingInputDTO;
import com.cqut.czb.bn.entity.dto.consultingManagement.HandleConsultationInputDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.consultingManagement.IConsultingManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/consultingManagement")
public class EnterpriseConsultingManagementController {
    @Autowired
    IConsultingManagementService consultingManagementService;

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/getConsultingInfoList",method = RequestMethod.GET)
    public JSONResult getConsultingInfoList( ConsultingInputDTO inputDTO){
        return  new JSONResult(consultingManagementService.getConsultingList(inputDTO));
    }

    @RequestMapping(value = "/handleConsultation",method = RequestMethod.POST)
    public JSONResult handleConsultation(@RequestBody HandleConsultationInputDTO inputDTO){
        return new JSONResult(consultingManagementService.handleConsultation(inputDTO));
    }

    @RequestMapping(value ="/insertConsultation",method = RequestMethod.POST)
    public JSONResult insertConsultation(@RequestBody ConsultingInputDTO inputDTO,Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        inputDTO.setApplicantAccount(user.getUserAccount());
        inputDTO.setApplicantId(user.getUserId());
        return new JSONResult(consultingManagementService.insertConsultation(inputDTO)>0);
    }

    @RequestMapping(value = "/getLastConsultation",method = RequestMethod.GET)
    public JSONResult getLastConsultation(Principal principal){

        User user = (User)redisUtils.get(principal.getName());

        return new JSONResult(consultingManagementService.getLastConsultation(user.getUserId()));
    }
}
