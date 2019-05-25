package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appHomePage.petrolPriceReportDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetrolPriceReportMapperExtra {
    /**
     * 根据地区查出所有的油价信息
     * @param area
     * @return
     */
    List<petrolPriceReportDTO> selectAll(@Param("area") String area);
}
