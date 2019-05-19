package com.cqut.czb.bn.api.controller.backOfEnterpriseContract;

import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.ContractIdInfo;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.backOfEnterpriseContract.EnterpriseContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enterpriseContract")
public class EnterpriseContractController {
    @Autowired
    private EnterpriseContractService contractService;

    @RequestMapping(value = "/getBasisInfo", method = RequestMethod.POST)
    public JSONResult getBasisInfo(@RequestBody ContractIdInfo info){
        return new JSONResult(contractService.getBasisInfo(info.getContractId()));
    }

    @RequestMapping(value = "/getPayInfoList", method =  RequestMethod.POST)
    public JSONResult getPayInfoList(@RequestBody ContractIdInfo info){
        return new JSONResult(contractService.getPayInfoList(info.getContractId()));
    }
}
