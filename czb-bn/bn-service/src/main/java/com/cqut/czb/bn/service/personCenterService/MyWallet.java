package com.cqut.czb.bn.service.personCenterService;

import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.BalanceAndInfoIdDTO;


public interface  MyWallet {
    int withDraw(AlipayRecordDTO alipayRecordDTO, String userId);

    BalanceAndInfoIdDTO getBalance(String userId);
}
