package com.cqut.czb.bn.dao.mapper.autoRecharge;

import com.cqut.czb.bn.entity.dto.AccountRechargeDTO;
import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfflineDistributorOfAdministratorMapperExtra {

    List<AccountRechargeDTO> getRechargeTableList(AccountRechargeDTO accountRechargeDTO);

    List<OfflineConsumptionDTO> getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO);

    List<OfflineClientDTO> getOfflineClientList(OfflineClientDTO offlineClientDTO);
}
