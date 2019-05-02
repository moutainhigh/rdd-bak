package com.cqut.czb.bn.service.personCenterService;

import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


public interface  MyWallet {
    String withDraw(AlipayRecordDTO alipayRecordDTO, String keyWord);

    JSONObject getBalance();
}
