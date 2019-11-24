package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;

import java.util.List;

public interface WeChatCommodityMapperExtra {
    List<WxCommodityDTO> selectAllCommodity(WxCommodityDTO wxCommodityDTO);
}
