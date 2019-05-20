package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.entity.entity.PayToPerson;

import java.util.List;

public interface PayToPersonMapperExtra {
    int deleteByPrimaryKey(String recordId);

    int insert(PayToPerson record);

    int insertSelective(PayToPerson record);

    List<PayToPersonDTO> selectByPrimaryKey(PayToPersonDTO payToPersonDTO);

    int updateByPrimaryKeySelective(PayToPerson record);

    int updateByPrimaryKey(PayToPerson record);

    int updateImportData (List<PayToPersonDTO> list);
}
