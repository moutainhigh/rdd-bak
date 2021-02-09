package com.cqut.czb.bn.dao.mapper.wCardAndPICCode;

import com.cqut.czb.bn.entity.dto.wCardAndPICCode.WCardAndPICCodeDTO;

import java.util.List;

public interface WCardAndPICCodeMapper {

    List<WCardAndPICCodeDTO> selectCommodityOrder(String userId);
}
