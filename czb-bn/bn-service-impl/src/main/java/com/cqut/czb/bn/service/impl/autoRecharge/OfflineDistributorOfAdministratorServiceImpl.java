package com.cqut.czb.bn.service.impl.autoRecharge;

import com.cqut.czb.bn.dao.mapper.autoRecharge.OfflineDistributorOfAdministratorMapperExtra;
import com.cqut.czb.bn.entity.dto.*;
import com.cqut.czb.bn.entity.dto.VIPRechargeRecord.VipRechargeRecordDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.OfflineDistributorOfAdministratorService;
import com.cqut.czb.bn.util.string.StringUtil;
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
        OfflineRecordsDTO rechargeRecordDTO = new OfflineRecordsDTO();
        rechargeRecordDTO.setOfflineRecordsListDTOList(new PageInfo<>(offlineDistributorOfAdministratorMapperExtra.getRechargeTableList(accountRechargeDTO)));
        rechargeRecordDTO.setTotalRecharge(offlineDistributorOfAdministratorMapperExtra.getTotalRecharge());
        return new JSONResult("列表数据查询成功", 200, rechargeRecordDTO);
    }

    @Override
    public JSONResult getOfflineConsumptionList(OfflineConsumptionDTO offlineConsumptionDTO) {
        PageHelper.startPage(offlineConsumptionDTO.getCurrentPage(), offlineConsumptionDTO.getPageSize(),true);
        OfflineRecordsDTO consumptionRecordDTO = new OfflineRecordsDTO();
        consumptionRecordDTO.setOfflineRecordsListDTOList(new PageInfo<>(offlineDistributorOfAdministratorMapperExtra.getOfflineConsumptionList(offlineConsumptionDTO)));
        consumptionRecordDTO.setTotalSale(offlineDistributorOfAdministratorMapperExtra.getTotalSale());
        return new JSONResult("列表数据查询成功", 200, consumptionRecordDTO);
    }

    @Override
    public JSONResult getOfflineClientList(OfflineClientDTO offlineClientDTO) {
        PageHelper.startPage(offlineClientDTO.getCurrentPage(), offlineClientDTO.getPageSize(),true);
        List<OfflineClientDTO> list = offlineDistributorOfAdministratorMapperExtra.getOfflineClientList(offlineClientDTO);
        PageInfo<OfflineClientDTO> pageInfo = new PageInfo<>(list);
        JSONResult result = new JSONResult("列表数据查询成功", 200, pageInfo);
        return  result;
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

    @Override
    public JSONResult recharge(RechargeDTO rechargeDTO) {
        if (rechargeDTO.getRechargeAmount()<0){
            return new JSONResult("充值金额不能为负数，充值失败",200);
        }
        if (offlineDistributorOfAdministratorMapperExtra.selectAccount(rechargeDTO.getAccount())==0){
            return new JSONResult("该账户不是线下大客户，充值失败",200);
        }
        else if (rechargeDTO.getAccount()==null || rechargeDTO.getAccount() == ""){
            RechargeDTO rechargeInfo = offlineDistributorOfAdministratorMapperExtra.getInfo(rechargeDTO);
            rechargeDTO.setUserId(rechargeInfo.getUserId());
            rechargeDTO.setInfoId(rechargeInfo.getInfoId());
            rechargeDTO.setRecordId(StringUtil.createId());
            rechargeDTO.setBalance(rechargeInfo.getBalance());
            offlineDistributorOfAdministratorMapperExtra.insertIncomeInfo(rechargeDTO);
            rechargeDTO.setRecordId(StringUtil.createId());
            offlineDistributorOfAdministratorMapperExtra.insertOfflineRecords(rechargeDTO);
            offlineDistributorOfAdministratorMapperExtra.updateBalance(rechargeDTO);
            return new JSONResult("充值成功",200);
        }
        return new JSONResult("充值失败",200);
    }
}
