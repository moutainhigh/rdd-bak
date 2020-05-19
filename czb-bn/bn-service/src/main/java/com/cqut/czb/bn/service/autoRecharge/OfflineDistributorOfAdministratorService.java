package com.cqut.czb.bn.service.autoRecharge;

import com.cqut.czb.bn.entity.dto.AccountRechargeDTO;
import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import com.cqut.czb.bn.entity.dto.RechargeDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;

public interface OfflineDistributorOfAdministratorService {

    JSONResult getRechargeTableList(AccountRechargeDTO accountRechargeDTO);

    JSONResult getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO);

    JSONResult getOfflineClientList(OfflineClientDTO offlineClientDTO);

    JSONResult getRechargeAccountList();

    JSONResult getAccountBalance(String account);

    JSONResult rechargeAndTurn(RechargeDTO rechargeDTO);

    Workbook exportRechargeRecords(AccountRechargeDTO accountRechargeDTO) throws Exception;

    Workbook exportConsumptionRecords(OfflineConsumptionDTO offlineConsumptionDTO) throws Exception;

    Workbook exportClientRecords(OfflineClientDTO offlineClientDTO) throws Exception;

    JSONResult passwordVerification(String password);

    JSONResult passwordModification(String OldPWD,String NewPWD);
}
