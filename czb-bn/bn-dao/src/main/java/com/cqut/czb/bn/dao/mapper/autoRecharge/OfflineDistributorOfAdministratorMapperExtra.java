package com.cqut.czb.bn.dao.mapper.autoRecharge;

import com.cqut.czb.bn.entity.dto.AccountRechargeDTO;
import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import com.cqut.czb.bn.entity.dto.RechargeDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfflineDistributorOfAdministratorMapperExtra {

    List<AccountRechargeDTO> getRechargeTableList(AccountRechargeDTO accountRechargeDTO);

    List<OfflineConsumptionDTO> getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO);

    List<OfflineClientDTO> getOfflineClientList(OfflineClientDTO offlineClientDTO);

    List<String> getRechargeAccountList(String account);

    double getAccountBalance(String account);

    double getTotalRecharge();

    double getTotalSale();

    RechargeDTO getInfo(RechargeDTO rechargeDTO);

    int insertIncomeInfo(RechargeDTO rechargeDTO);

    int insertOfflineRecords(RechargeDTO rechargeDTO);

    int selectAccount(String account);

    int updateBalance(RechargeDTO rechargeDTO);
}
