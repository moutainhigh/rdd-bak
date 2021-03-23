package com.cqut.czb.bn.service.wCardAndPICCode;

import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.dto.wCardAndPICCode.WCardAndPICCodeDTO;

import java.util.List;

public interface WCardAndPICCodeService {

    List<H5StockDTO> getCommodityOrder(String userId, Integer type);
}
