package com.cqut.czb.bn.service.personCenterService;

import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.BalanceAndInfoIdDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.WeiXinRecordDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import net.sf.json.JSONObject;

import java.math.BigDecimal;


public interface MyWallet {
    JSONResult withDraw(AlipayRecordDTO alipayRecordDTO, String userId);

    BalanceAndInfoIdDTO getBalance(String userId);

    JSONObject getWithdrawLog(String userId);

    /**
     * 微信小程序，提现到微信
     *
     * @return
     */
    JSONResult wspWithDrawToWeChat(BigDecimal paymentAmount, User user);

    JSONResult wxWithDraw(User user , WeiXinRecordDTO weiXinRecordDTO);

    JSONResult wxPostDraw(User user, WeiXinRecordDTO weiXinRecordDTO);
}
