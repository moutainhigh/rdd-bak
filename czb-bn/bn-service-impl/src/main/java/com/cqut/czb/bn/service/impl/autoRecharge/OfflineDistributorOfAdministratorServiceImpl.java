package com.cqut.czb.bn.service.impl.autoRecharge;

import com.cqut.czb.bn.dao.mapper.autoRecharge.OfflineDistributorOfAdministratorMapperExtra;
import com.cqut.czb.bn.entity.dto.AccountRechargeDTO;
import com.cqut.czb.bn.entity.dto.OfflineClientDTO;
import com.cqut.czb.bn.entity.dto.OfflineConsumptionDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.OfflineDistributorOfAdministratorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfflineDistributorOfAdministratorServiceImpl implements OfflineDistributorOfAdministratorService {

    @Autowired
    OfflineDistributorOfAdministratorMapperExtra offlineDistributorOfAdministratorMapperExtra;

    @Override
    public JSONResult getRechargeTableList(AccountRechargeDTO accountRechargeDTO) {
        PageHelper.startPage(accountRechargeDTO.getCurrentPage(), accountRechargeDTO.getPageSize(),true);
        List<AccountRechargeDTO> list = offlineDistributorOfAdministratorMapperExtra.getRechargeTableList(accountRechargeDTO);
        PageInfo<AccountRechargeDTO> pageInfo = new PageInfo<>(list);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO) {
        PageHelper.startPage(offlineConsumptionDTO.getCurrentPage(), offlineConsumptionDTO.getPageSize(),true);
        List<OfflineConsumptionDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineConsumptionList(offlineConsumptionDTO);
        PageInfo<OfflineConsumptionDTO> pageInfo = new PageInfo<>(list);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getOfflineClientList(OfflineClientDTO offlineClientDTO) {
        PageHelper.startPage(offlineClientDTO.getCurrentPage(), offlineClientDTO.getPageSize(),true);
        List<OfflineClientDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineClientList(offlineClientDTO);
        PageInfo<OfflineClientDTO> pageInfo = new PageInfo<>(list);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getRechargeAccountList(String account) {
        List<String> list = offlineDistributorOfAdministratorMapperExtra.getRechargeAccountList(account);
        return new JSONResult("列表查询成功", 200, list);
    }

    @Override
    public JSONResult getAccountBalance(String account) {
        double balance = offlineDistributorOfAdministratorMapperExtra.getAccountBalance(account);
        return new JSONResult("余额查询成功", 200, balance);
    }
}
