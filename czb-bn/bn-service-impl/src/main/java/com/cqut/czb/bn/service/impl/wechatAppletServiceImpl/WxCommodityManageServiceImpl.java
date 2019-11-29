package com.cqut.czb.bn.service.impl.wechatAppletServiceImpl;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.Category;
import com.cqut.czb.bn.service.wechatAppletService.WxCommodityManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @Override
    public Boolean addWxCommodity(WxCommodityDTO wxCommodityDTO, MultipartFile file, User user) throws IOException {
        return null;
    }

    @Override
    public Boolean addWxCommodityImg(WxCommodityDTO wxCommodityDTO, MultipartFile file, User user) throws IOException {
        return null;
    }

    @Override
    public Boolean updateCommodity(WxCommodityDTO wxCommodityDTO) {
        return null;
    }

    @Override
    public Boolean deleteWxCommodityImg(String fileId, String id) {
        return null;
    }

    @Override
    public List<Category> selectAllCategory() {
        return null;
    }
}
