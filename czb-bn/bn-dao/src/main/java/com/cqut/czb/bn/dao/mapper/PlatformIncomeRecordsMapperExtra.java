package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.entity.PlatformIncomeRecords;

import java.util.List;

public interface PlatformIncomeRecordsMapperExtra {

    int deleteByPrimaryKey(String recordId);

    int insert(PlatformIncomeRecords record);

    int insertSelective(PlatformIncomeRecords record);

    List<PlatformIncomeRecordsDTO> selectByPrimaryKey(PlatformIncomeRecordsDTO platformIncomeRecordsDTO);

    int updateByPrimaryKeySelective(PlatformIncomeRecords record);

    int updateByPrimaryKey(PlatformIncomeRecords record);

}
