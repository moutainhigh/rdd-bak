package com.cqut.czb.bn.service.wechatAppletService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;
import com.github.pagehelper.PageInfo;

public interface WxCommodityManageService {
    PageInfo<WxCommodityDTO> getAllCommodity(WxCommodityDTO wxCommodityDTO, PageDTO pageDTO);
}
