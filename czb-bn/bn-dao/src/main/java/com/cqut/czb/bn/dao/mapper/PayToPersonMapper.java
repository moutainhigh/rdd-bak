package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.PayToPerson;

public interface PayToPersonMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(PayToPerson record);

    int insertSelective(PayToPerson record);

    PayToPerson selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(PayToPerson record);

    int updateByPrimaryKey(PayToPerson record);
}