package com.cqut.czb.bn.service.personCenterService;

import com.cqut.czb.bn.entity.dto.personCenter.myWallet.AlipayRecordDTO;
import com.cqut.czb.bn.entity.dto.personCenter.myWallet.LoginInfoDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public interface  MyWallet {
    String withDraw(AlipayRecordDTO alipayRecordDTO, String keyWord);
}
