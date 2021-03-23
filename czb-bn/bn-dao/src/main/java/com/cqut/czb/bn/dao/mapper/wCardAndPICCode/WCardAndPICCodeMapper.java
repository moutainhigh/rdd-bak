package com.cqut.czb.bn.dao.mapper.wCardAndPICCode;

import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.dto.wCardAndPICCode.WCardAndPICCodeDTO;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Repository
public interface WCardAndPICCodeMapper {

    List<H5StockDTO> selectCommodityOrder(@Param("userId") String userId, @Param("recordType")Integer type);
}
