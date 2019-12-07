package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatSettleRecord;

public interface WeChatSettleRecordMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(WeChatSettleRecord record);

    int insertSelective(WeChatSettleRecord record);

    WeChatSettleRecord selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(WeChatSettleRecord record);

    int updateByPrimaryKey(WeChatSettleRecord record);
}