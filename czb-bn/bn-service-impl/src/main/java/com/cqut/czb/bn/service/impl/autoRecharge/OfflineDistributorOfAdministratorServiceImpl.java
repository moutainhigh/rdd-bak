package com.cqut.czb.bn.service.impl.autoRecharge;

import com.cqut.czb.bn.dao.mapper.autoRecharge.OfflineDistributorOfAdministratorMapperExtra;
import com.cqut.czb.bn.entity.dto.AccountRechargeDTO;
import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.OfflineDistributorOfAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfflineDistributorOfAdministratorServiceImpl implements OfflineDistributorOfAdministratorService {

    @Autowired
    OfflineDistributorOfAdministratorMapperExtra offlineDistributorOfAdministratorMapperExtra;

    @Override
    public JSONResult getRechargeTableList(AccountRechargeDTO accountRechargeDTO) {
        List<AccountRechargeDTO> list = offlineDistributorOfAdministratorMapperExtra.getRechargeTableList(accountRechargeDTO);
        return new JSONResult("记录查询成功", 200, list);
    }

    @Override
    public JSONResult getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO) {
        List<OfflineConsumptionDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineConsumptionList(offlineConsumptionDTO);
        return new JSONResult("记录查询成功", 200, list);
    }

    @Override
    public JSONResult getOfflineClientList(OfflineClientDTO offlineClientDTO) {
        List<OfflineClientDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineClientList(offlineClientDTO);
        return new JSONResult("信息查询成功", 200, list);
    }
}
