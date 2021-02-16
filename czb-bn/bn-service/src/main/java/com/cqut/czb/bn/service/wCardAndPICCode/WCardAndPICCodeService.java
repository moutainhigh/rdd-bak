package com.cqut.czb.bn.service.wCardAndPICCode;

import com.cqut.czb.bn.entity.dto.wCardAndPICCode.WCardAndPICCodeDTO;

import java.util.List;

public interface WCardAndPICCodeService {

    List<WCardAndPICCodeDTO> getCommodityOrder(String userId, Integer type);
}
