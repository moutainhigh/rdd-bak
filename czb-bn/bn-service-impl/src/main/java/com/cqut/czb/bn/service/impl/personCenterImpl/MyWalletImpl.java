package com.cqut.czb.bn.service.impl.personCenterImpl;

import com.cqut.czb.bn.dao.mapper.MyWalletMapper;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.LoginInfoDTO;
import com.cqut.czb.bn.service.personCenter.MyWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MyWalletImpl implements MyWallet {
    @Autowired
    private MyWalletMapper myWalletMapper;

    @Override
    public String withDraw(AlipayRecordDTO alipayRecordDTO, LoginInfoDTO loginInfoDTO, String keyWord){
        BigDecimal balance = new BigDecimal(myWalletMapper.getUserAllIncome(loginInfoDTO.getUserId()));

        if(alipayRecordDTO.getPaymentAmount().compareTo(new BigDecimal(0)) == -1){
            return new String("余额不能是负数");
        }

        if(alipayRecordDTO.getPaymentAmount().compareTo(balance) == 1){
            return new String("提现金额超出余额");
        }


        return new String("");
    }
}
