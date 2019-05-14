package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.AppInfoDTO;
import com.cqut.czb.bn.entity.entity.Dict;

import java.util.List;

public interface IDictService {

    List<Dict> selectDictList();

    List<Dict> selectCustomerServiceStaff();

    AppInfoDTO selectAndroidInfo();

    AppInfoDTO selectIOSInfo();
}
