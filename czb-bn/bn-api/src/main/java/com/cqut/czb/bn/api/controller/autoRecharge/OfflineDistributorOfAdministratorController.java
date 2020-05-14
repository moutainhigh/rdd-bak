package com.cqut.czb.bn.api.controller.autoRecharge;

import com.cqut.czb.auth.interceptor.PermissionCheck;

import com.cqut.czb.bn.entity.dto.AccountRechargeDTO;
import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.OfflineDistributorOfAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/OfflineDistributorRecords")
public class OfflineDistributorOfAdministratorController {
    @Autowired
    OfflineDistributorOfAdministratorService offlineDistributorOfAdministratorService;

    /**
     * 获取账户充值记录
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getRechargeTableList",method = RequestMethod.POST)
    public JSONResult getRechargeTableList(AccountRechargeDTO accountRechargeDTO){
        return offlineDistributorOfAdministratorService.getRechargeTableList(accountRechargeDTO);
    }

    /**
     * 获取所有线下消费记录
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getOfflineConsumptionList",method = RequestMethod.POST)
    public JSONResult getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO){
        return offlineDistributorOfAdministratorService.getOfflineConsumptionList(offlineConsumptionDTO);
    }

    /**
     * 获取代理商信息
     */
    @Transactional(rollbackFor = {RuntimeException.class,Error.class})
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getOfflineClientList",method = RequestMethod.POST)
    public JSONResult getOfflineClientList(OfflineClientDTO offlineClientDTO){
        return offlineDistributorOfAdministratorService.getOfflineClientList(offlineClientDTO);
    }
}
