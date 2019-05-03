package com.cqut.czb.bn.service.personCenterService;

import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


public interface  MyWallet {
    int withDraw(AlipayRecordDTO alipayRecordDTO, String userId);

    JSONObject getBalance(String userId);
}
