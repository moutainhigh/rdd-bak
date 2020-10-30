package com.cqut.czb.bn.dao.mapper.autoRecharge;

import com.cqut.czb.bn.entity.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfflineDistributorOfAdministratorMapperExtra {

    List<AccountRechargeDTO> getRechargeTableList(AccountRechargeDTO accountRechargeDTO);

    List<OfflineConsumptionDTO> getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO);

    List<OfflineClientDTO> getOfflineClientList(OfflineClientDTO offlineClientDTO);

    List<String> getRechargeAccountList(Integer isSpecial);

    Double getAccountBalance(String account);

    BeforeBalanceDTO getTotalBalance(OfflineConsumptionDTO offlineConsumptionDTO);

    Double getTotalRecharge(AccountRechargeDTO accountRechargeDTO);

    Double getTotalSale(OfflineConsumptionDTO offlineConsumptionDTO);

    Double getTotalTurn(AccountRechargeDTO accountRechargeDTO);

    RechargeDTO getInfo(RechargeDTO rechargeDTO);

    int insertIncomeInfo(RechargeDTO rechargeDTO);

    int insertOfflineRecords(RechargeDTO rechargeDTO);

    int selectAccount(String account);

    int updateBalance(RechargeDTO rechargeDTO);

    String getPassword();

    String getSpecialPassword();

    int changePWD(String newPWD);

    int changeSpecialPWD(String newPWD);

    int insertPassword(String newPWD);

    int insertSpecialPassword(String newPWD);

}
