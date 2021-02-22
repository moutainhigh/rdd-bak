package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.dto.integral.CommodityDetailsDTO;
import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingCommodity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DirectChargingCommodityMapperExtra {

    /**
     * 用于后台管理系统
     * @return
     */
    List<CommodityDetailsDTO> selectAllCommodityTitle(@Param("commodityType1") String commodityType1, @Param("commodityType2") String commodityType2);

}
