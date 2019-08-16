package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.RiderEvaluateDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.RiderEvaluate;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.EvaluateManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/evaluateManage")
@RestController
public class EvaluateManageController {

    @Autowired
    EvaluateManageService evaluateManageService;

    @GetMapping("/getEvaluateList")
    public JSONResult getEvaluateList(RiderEvaluateDTO riderEvaluateDTO, PageDTO pageDTO){
        return new JSONResult(evaluateManageService.getEvaluateList(riderEvaluateDTO,pageDTO));
    }
}
