package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Petrol;

public interface PetrolMapper {
    int deleteByPrimaryKey(String petrolId);

    int insert(Petrol record);

    int insertSelective(Petrol record);

    Petrol selectByPrimaryKey(String petrolId);

    int updateByPrimaryKeySelective(Petrol record);

    int updateByPrimaryKey(Petrol record);
}