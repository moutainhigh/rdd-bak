package com.cqut.czb.bn.api.controller.automaticRecharge;

import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.automaticRechargeService.AutomaticRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/automaticRecharge")
public class AutomaticRechargeController {

    @Autowired
    AutomaticRechargeService automaticRechargeService;

    /**
     * 获取信息
     * @return
     */
    @RequestMapping(value = "/getTableList",method = RequestMethod.POST)
    public JSONResult getOneServiceInfo(AutomaticRechargeDTO automaticRechargeDTO){
        return automaticRechargeService.getAutoList(automaticRechargeDTO);
    }

    /**
     * 删除信息
     * @return
     */
    @RequestMapping(value = "/deleteRecorder",method = RequestMethod.POST)
    public JSONResult getOneServiceInfo(String id){
        return automaticRechargeService.deleteRecorder(id);
    }
}
