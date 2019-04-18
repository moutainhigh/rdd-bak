package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.CarsPersons;

public interface CarsPersonsMapper {
    int deleteByPrimaryKey(String personCarId);

    int insert(CarsPersons record);

    int insertSelective(CarsPersons record);

    CarsPersons selectByPrimaryKey(String personCarId);

    int updateByPrimaryKeySelective(CarsPersons record);

    int updateByPrimaryKey(CarsPersons record);
}