package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.VIPApply;

import java.util.List;

public interface WeChatVipApplyMapperExtra {

    List<VIPApply> selectVipApply(VIPApply vipApply);

    boolean updateVipApply(VIPApply vipApply);
}
