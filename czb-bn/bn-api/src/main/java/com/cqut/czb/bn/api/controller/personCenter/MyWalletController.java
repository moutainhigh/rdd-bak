package com.cqut.czb.bn.api.controller.personCenter;

import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.LoginInfoDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.personCenterService.MyWallet;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者：谭深化
 * 模块：app个人中心，我的钱包
 * 业务：提现
 */
@RestController
@RequestMapping("/personCenter")
public class MyWalletController {

    @Autowired
    private MyWallet myWallet;

    @RequestMapping(value = "/myWallet/withDraw", method = RequestMethod.POST)
    public JSONResult withDraw(AlipayRecordDTO alipayRecordDTO, LoginInfoDTO loginInfoDTO, String keyWord){
        JSONResult jsonResult = new JSONResult();
        jsonResult.setMessage(myWallet.withDraw(alipayRecordDTO, loginInfoDTO, keyWord));
        return jsonResult;
    }
}
