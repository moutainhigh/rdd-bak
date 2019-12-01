package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.PetrolPriceReport;

public interface PetrolPriceReportMapper {
    int deleteByPrimaryKey(String petrolPriceReportId);

    int insert(PetrolPriceReport record);

    int insertSelective(PetrolPriceReport record);

    PetrolPriceReport selectByPrimaryKey(String petrolPriceReportId);

    int updateByPrimaryKeySelective(PetrolPriceReport record);

    int updateByPrimaryKey(PetrolPriceReport record);
}