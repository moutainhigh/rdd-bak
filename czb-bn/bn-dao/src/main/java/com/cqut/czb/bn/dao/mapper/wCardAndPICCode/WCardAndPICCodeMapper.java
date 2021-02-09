package com.cqut.czb.bn.dao.mapper.wCardAndPICCode;

import com.cqut.czb.bn.entity.dto.wCardAndPICCode.WCardAndPICCodeDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WCardAndPICCodeMapper {

    List<WCardAndPICCodeDTO> selectCommodityOrder(String userId, int type);
}
