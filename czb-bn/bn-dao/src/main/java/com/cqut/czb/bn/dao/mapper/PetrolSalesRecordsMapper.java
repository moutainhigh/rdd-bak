package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetrolSalesRecordsMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(PetrolSalesRecords record);

    int insertSelective(PetrolSalesRecords record);

    PetrolSalesRecords selectByPrimaryKey(String recordId);

    List<PetrolSalesRecords> selectAll();

    int updateMenus(PetrolSalesRecords list);

    int updateByPrimaryKeySelective(PetrolSalesRecords record);

    int updateByPrimaryKey(PetrolSalesRecords record);
}