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
    public JSONResult getTableList(AutomaticRechargeDTO automaticRechargeDTO){
        return automaticRechargeService.getAutoList(automaticRechargeDTO);
    }

    /**
     * 删除信息
     * @return
     */
    @RequestMapping(value = "/deleteRecorder",method = RequestMethod.POST)
    public JSONResult deleteRecorder(String id){
        return automaticRechargeService.deleteRecorder(id);
    }

    /**
     * 修改信息
     * @return
     */
    @RequestMapping(value = "/editRecorder",method = RequestMethod.POST)
    public JSONResult editRecorder(AutomaticRechargeDTO automaticRechargeDTO){
        return automaticRechargeService.editRecorder(automaticRechargeDTO);
    }

    /**
     * 查看信息
     * @return
     */
    @RequestMapping(value = "/showRecorder",method = RequestMethod.POST)
    public JSONResult showRecorder(String id){
        return automaticRechargeService.showRecorder(id);
    }
}
