package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatVipApply;

public interface WeChatVipApplyMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(WeChatVipApply record);

    int insertSelective(WeChatVipApply record);

    WeChatVipApply selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(WeChatVipApply record);

    int updateByPrimaryKey(WeChatVipApply record);
}