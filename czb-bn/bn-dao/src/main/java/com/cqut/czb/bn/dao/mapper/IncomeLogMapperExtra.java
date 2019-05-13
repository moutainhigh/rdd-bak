package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appPersonalCenter.MyIncomeLogDTO;
import com.cqut.czb.bn.entity.entity.IncomeLog;

import java.util.List;

public interface IncomeLogMapperExtra {
    int deleteByPrimaryKey(String recordId);

    int insert(IncomeLog record);

    int insertSelective(IncomeLog record);

    IncomeLog selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(IncomeLog record);

    int updateByPrimaryKey(IncomeLog record);

    List<MyIncomeLogDTO>  selectIncomeLog(MyIncomeLogDTO myIncomeLogDTO);

}