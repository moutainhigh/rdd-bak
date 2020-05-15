package com.cqut.czb.bn.service.autoRecharge;

import com.cqut.czb.bn.entity.dto.AccountRechargeDTO;
import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import com.cqut.czb.bn.entity.dto.RechargeDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface OfflineDistributorOfAdministratorService {

    JSONResult getRechargeTableList(AccountRechargeDTO accountRechargeDTO);

    JSONResult getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO);

    JSONResult getOfflineClientList(OfflineClientDTO offlineClientDTO);

    JSONResult getRechargeAccountList();

    JSONResult getAccountBalance(String account);

    JSONResult recharge(RechargeDTO rechargeDTO);
}
