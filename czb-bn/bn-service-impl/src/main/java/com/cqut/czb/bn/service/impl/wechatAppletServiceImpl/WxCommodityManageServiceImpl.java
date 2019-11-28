package com.cqut.czb.bn.service.impl.wechatAppletServiceImpl;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;
import com.cqut.czb.bn.service.wechatAppletService.WxCommodityManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WxCommodityManageServiceImpl implements WxCommodityManageService {

    @Autowired
    WeChatCommodityMapperExtra weChatCommodityMapperExtra;

    @Override
    public PageInfo<WxCommodityDTO> getAllCommodity(WxCommodityDTO wxCommodityDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(weChatCommodityMapperExtra.selectAllCommodity(wxCommodityDTO));
    }

    @Override
    public Boolean deletedWxCommodity(String commodityId) {
        return weChatCommodityMapperExtra.deleteByPrimaryKey(commodityId)>0;
    }
}
