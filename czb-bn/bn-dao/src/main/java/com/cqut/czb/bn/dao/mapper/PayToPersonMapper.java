package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.payToPerson.PayToPersonDTO;
import com.cqut.czb.bn.entity.entity.PayToPerson;

import java.util.List;

public interface PayToPersonMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(List<PayToPersonDTO> list);

    int insertSelective(PayToPerson record);

    PayToPerson selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(PayToPerson record);

    int updateByPrimaryKey(PayToPerson record);
}