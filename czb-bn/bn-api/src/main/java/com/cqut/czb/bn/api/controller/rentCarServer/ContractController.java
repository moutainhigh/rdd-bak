package com.cqut.czb.bn.api.controller.rentCarServer;

import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.rentCarService.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者：谭深哈
 * 模块：租车服务-合同
 * 业务：个人租车合同、平台企业套餐合同签署
 */

@RestController
@RequestMapping("/signContract")
public class ContractController {
    @Autowired
    private ContractService contractService;

    /**
     * 获得平台在云合同长效令牌token，客户端需要定时更新，后续操作都需要传入token
     * @return
     */
    @RequestMapping(value = "/getContractToken", method = RequestMethod.GET)
    public JSONResult getContractToken(){
        return new JSONResult(contractService.getContractToken());
    }

    /**
     * 给用户（个人、或企业）注册云合同唯一id，此id需存入数据库维护
     * @param token
     * @return
     */
    @RequestMapping(value = "/registerPersonalContractAccount", method = RequestMethod.POST)
    public JSONResult registerPersonalContractAccount(String token){
        return new JSONResult();
    }
}
