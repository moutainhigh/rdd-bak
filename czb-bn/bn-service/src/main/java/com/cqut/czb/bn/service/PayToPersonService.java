package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.entity.entity.PayToPerson;
import com.github.pagehelper.PageInfo;

public interface PayToPersonService {

    PageInfo<PayToPerson> getPayList(PayToPersonDTO payToPersonDTO , PageDTO pageDTO);

}
