package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WechatNorms;

public interface WechatNormsMapper {
    int deleteByPrimaryKey(String normsId);

    int insert(WechatNorms record);

    int insertSelective(WechatNorms record);

    WechatNorms selectByPrimaryKey(String normsId);

    int updateByPrimaryKeySelective(WechatNorms record);

    int updateByPrimaryKey(WechatNorms record);
}