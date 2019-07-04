package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.MsgRecord;

public interface MsgRecordMapper {
    int deleteByPrimaryKey(String msgRecordId);

    int insert(MsgRecord record);

    int insertSelective(MsgRecord record);

    MsgRecord selectByPrimaryKey(String msgRecordId);

    int updateByPrimaryKeySelective(MsgRecord record);

    int updateByPrimaryKey(MsgRecord record);
}