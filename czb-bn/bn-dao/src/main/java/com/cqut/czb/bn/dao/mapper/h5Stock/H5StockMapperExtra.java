package com.cqut.czb.bn.dao.mapper.h5Stock;

import com.cqut.czb.bn.entity.dto.integral.CommodityDetailsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface H5StockMapperExtra {
    List<CommodityDetailsDTO> selectAllCommodityTitle(@Param("commodityType1") String commodityType1, @Param("commodityType2") String commodityType2);
}
