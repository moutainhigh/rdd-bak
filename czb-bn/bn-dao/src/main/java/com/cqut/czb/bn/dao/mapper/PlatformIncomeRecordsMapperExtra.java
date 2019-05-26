package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.platformIncomeRecords.PlatformIncomeRecordsDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PlatformIncomeRecords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlatformIncomeRecordsMapperExtra {

    int deleteByPrimaryKey(String recordId);

    int insert(List<PlatformIncomeRecordsDTO> list);

    int insertSelective(PlatformIncomeRecords record);

    List<PlatformIncomeRecordsDTO> selectByPrimaryKey(PlatformIncomeRecordsDTO platformIncomeRecordsDTO);

    List<PlatformIncomeRecordsDTO> selectIncome(List<PlatformIncomeRecordsDTO> list);

    List<PlatformIncomeRecordsDTO> selectIncomeList(PlatformIncomeRecordsDTO platformIncomeRecordsDTO);

    int updateImportData(List<PlatformIncomeRecordsDTO> list);

    int updateByPrimaryKeySelective(PlatformIncomeRecords record);

    int updateByPrimaryKey(PlatformIncomeRecords record);

    List<Petrol> selectPetrolList(PlatformIncomeRecordsDTO platformIncomeRecordsDTO);

    int selectIncomeState(String id);

    int selectContractMonth(String id);

    List<PlatformIncomeRecordsDTO> selectNewContract(@Param("list") List<PlatformIncomeRecordsDTO> list, @Param("platform") PlatformIncomeRecordsDTO platformIncomeRecordsDTO);

}
