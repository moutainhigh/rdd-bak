package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.MsgModel;

public interface MsgModelMapper {
    int deleteByPrimaryKey(String msgModelId);

    int insert(MsgModel record);

    int insertSelective(MsgModel record);

    MsgModel selectByPrimaryKey(String msgModelId);

    int updateByPrimaryKeySelective(MsgModel record);

    int updateByPrimaryKey(MsgModel record);
}