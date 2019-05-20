package com.cqut.czb.bn.api.controller.backOfEnterpriseContract;

import com.cqut.czb.bn.entity.dto.backOfEnterpriseContract.PetrolIdInfo;
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

    /**
     * 获得合同基本信息
     * @param info
     * @return
     */
    @RequestMapping(value = "/getBasisInfo", method = RequestMethod.POST)
    public JSONResult getBasisInfo(@RequestBody ContractIdInfo info){
        return new JSONResult(contractService.getBasisInfo(info.getContractId()));
    }

    /**
     * 获得打款信息
     * @param info
     * @return
     */
    @RequestMapping(value = "/getPayInfoList", method =  RequestMethod.POST)
    public JSONResult getPayInfoList(@RequestBody ContractIdInfo info){
        return new JSONResult(contractService.getPayInfoList(info.getContractId()));
    }

    /**
     * 获得企业此合同已购油卡
     * @param info
     * @return
     */
    @RequestMapping(value = "/getPetrolInfoList", method =  RequestMethod.POST)
    public JSONResult getPetrolInfoList(@RequestBody ContractIdInfo info){
        return new JSONResult(contractService.getPetrolInfoList(info.getContractId()));
    }

    /**
     * 获得油卡充值信息
     * @param info
     * @return
     */
    @RequestMapping(value = "/getPetrolPayInfoList", method =  RequestMethod.POST)
    public JSONResult getPetrolPayInfoList(@RequestBody PetrolIdInfo info){
        return new JSONResult(contractService.getPetrolPayInfoList(info.getPetrolId()));
    }

}
