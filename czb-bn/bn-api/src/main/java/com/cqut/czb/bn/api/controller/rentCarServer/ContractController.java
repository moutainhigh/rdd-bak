package com.cqut.czb.bn.api.controller.rentCarServer;

import com.cqut.czb.bn.entity.dto.appRentCarContract.EnterpriseRegisterDTO;
import com.cqut.czb.bn.entity.dto.appRentCarContract.PersonalRegisterDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.rentCarService.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    /**
     * 获得平台在云合同长效令牌token，客户端需要定时更新，后续操作都需要传入token
     * @return token字符串
     */
    @RequestMapping(value = "/getContractToken", method = RequestMethod.POST)
    public JSONResult getContractToken(){
        return new JSONResult(contractService.getContractToken());
    }

    /**
     * 获取合同id
     */
    @RequestMapping(value = "/getContractId", method = RequestMethod.POST)
    public JSONResult getContractId(){
        return new JSONResult(contractService.getContractId());
    }

    /**
     * 给用户（个人）注册云合同唯一id，此id需存入数据库维护
     * @param personalRegisterDTO,token
     * @return 云合同返回的用户（个人或企业）id
     */
    @RequestMapping(value = "/registerPersonalContractAccount", method = RequestMethod.POST)
    public JSONResult registerPersonalContractAccount(@Validated  PersonalRegisterDTO personalRegisterDTO, String token, Integer type){
        return new JSONResult(contractService.registerPersonalContractAccount(personalRegisterDTO, token));
    }

    /**
     * 企业注册云合同
     * 给用户（个人、或企业）注册云合同唯一id，此id需存入数据库维护
     * @param enterpriseRegisterDTO,token
     * @return 云合同返回的用户（个人或企业）id
     */
    @RequestMapping(value = "/registerEnterpriseContractAccount", method = RequestMethod.POST)
    public JSONResult registerEnterpriseContractAccount(@Validated EnterpriseRegisterDTO enterpriseRegisterDTO, String token, Integer type){
        return new JSONResult(contractService.registerEnterpriseContractAccount(enterpriseRegisterDTO, token));
    }

    /**
     * 根据合同模板生成合同（此时还未签署合同）
     */
    @RequestMapping(value = "/createContract", method = RequestMethod.POST)
    public JSONResult createContract(String token){
        return new JSONResult(contractService.createContract(token));
    }

    /**
     * 添加合同签署者
     */
    @RequestMapping(value = "/addContractOwner", method = RequestMethod.POST)
    public JSONResult downloadContract(String token){
        return new JSONResult(contractService.addContractOwner(token));
    }

    /**
     * 合同签署
     * 在生成模板、添加签署者之后，可以进行合同签署了
     */
    @RequestMapping(value = "/signerContract", method = RequestMethod.POST)
    public JSONResult signerContract(String token){
        return new JSONResult(contractService.signerContract(token));
    }

    /**
     * 合同存证
     * 签署合同后要进行存证，出事儿可进行公证
     */
    @RequestMapping(value = "/czContract", method= RequestMethod.POST)
    public JSONResult czContract(String token){
        return new JSONResult(contractService.czContract(token));
    }
}
