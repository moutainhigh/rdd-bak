package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatWithdrawMapperExtra;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatBalanceRecord;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatGetDetailedDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatTOWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatWithdrawDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.IncomeLogDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WCPWithdrawService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WCPWithdrawServiceImpl implements WCPWithdrawService {

    @Autowired
    WeChatWithdrawMapperExtra weChatWithdrawMapperExtra;

    @Override
    public JSONResult selectWithdrawInfo(WeChatWithdrawDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(),true);
        List<WeChatWithdrawDTO> withdrawList = weChatWithdrawMapperExtra.selectWithdrawList(pageDTO);
        PageInfo<WeChatWithdrawDTO> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public synchronized JSONResult toWithdraw(WeChatTOWithdrawDTO weChatTOWithdrawDTO) {

        if (weChatTOWithdrawDTO.getMoney().compareTo(new BigDecimal(0)) < 0) {
            return new JSONResult("提现金额不能是负数", 300, "提现金额不能是负数");
        }

        if (weChatTOWithdrawDTO.getMoney().compareTo(new BigDecimal("0.01")) < 0) {
            return new JSONResult("提现金额不能低于0.01元", 300, "提现金额不能低于0.01元");
        }

        // 取出余额，进行对比
        WeChatBalanceRecord weChatBalanceRecord = weChatWithdrawMapperExtra.getBalance(weChatTOWithdrawDTO.getInfoId());
        if (weChatTOWithdrawDTO.getMoney().compareTo(weChatBalanceRecord.getBalance()) > 0) {
            return new JSONResult("提现金额超出余额", 300, "提现金额超出余额");
        }

        // 设置提现记录基本信息
        IncomeLogDTO incomeLog = new IncomeLogDTO();
        incomeLog.setInfoId(weChatTOWithdrawDTO.getInfoId());
        incomeLog.setAmount((weChatTOWithdrawDTO.getMoney().doubleValue()));
        incomeLog.setBeforeChangeIncome(weChatBalanceRecord.getBalance().doubleValue());
        incomeLog.setRecordId(StringUtil.createId());
        incomeLog.setSourceId(StringUtil.createId());
        incomeLog.setRemark("提现");
        incomeLog.setType(1);
        incomeLog.setWithdrawAmount(weChatTOWithdrawDTO.getMoney().doubleValue());
        incomeLog.setWithdrawTo(weChatTOWithdrawDTO.getPaymentAccount());
        incomeLog.setWithdrawName(weChatTOWithdrawDTO.getPaymentName());

        // 更新用户已提现金额
        int updateBalance = weChatWithdrawMapperExtra.increaseWithdrawed(weChatTOWithdrawDTO.getInfoId(),weChatTOWithdrawDTO.getMoney().toString());
        if (updateBalance != 1)
            return new JSONResult("提现成功，但更新用户余额失败", 500, "提现成功");

        // 插入提现记录
        int insertSuccess = weChatWithdrawMapperExtra.insertIncomeLog(incomeLog);
        if (insertSuccess != 1){
            return new JSONResult("提现成功，但插入提现记录出错", 500, "提现成功");
        }else {
            return new JSONResult("提现成功", 200, "提现成功");
        }

    }

    @Override
    public JSONResult getDetailed(WeChatGetDetailedDTO weChatGetDetailedDTO) {
        PageHelper.startPage(weChatGetDetailedDTO.getCurrentPage(), weChatGetDetailedDTO.getPageSize(),true);
        List<WeChatGetDetailedDTO> weChatGetDetailedDTOList = weChatWithdrawMapperExtra.selectDetaileds(weChatGetDetailedDTO);
        PageInfo<WeChatGetDetailedDTO> pageInfo = new PageInfo<>(weChatGetDetailedDTOList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }
}
