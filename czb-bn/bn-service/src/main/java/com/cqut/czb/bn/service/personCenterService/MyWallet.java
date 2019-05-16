package com.cqut.czb.bn.service.personCenterService;

import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.BalanceAndInfoIdDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.WithDrawLogDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface  MyWallet {
    JSONResult withDraw(AlipayRecordDTO alipayRecordDTO, String userId);

    BalanceAndInfoIdDTO getBalance(String userId);

    JSONObject getWithdrawLog(String userId);
}
