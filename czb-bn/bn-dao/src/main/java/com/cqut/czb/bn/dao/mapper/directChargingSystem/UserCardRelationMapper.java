package com.cqut.czb.bn.dao.mapper.directChargingSystem;

import com.cqut.czb.bn.entity.entity.directChargingSystem.UserCardRelation;

public interface UserCardRelationMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(UserCardRelation record);

    int insertSelective(UserCardRelation record);

    UserCardRelation selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(UserCardRelation record);

    int updateByPrimaryKey(UserCardRelation record);
}