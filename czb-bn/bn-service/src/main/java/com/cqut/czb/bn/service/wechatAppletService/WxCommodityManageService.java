package com.cqut.czb.bn.service.wechatAppletService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.Category;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface WxCommodityManageService {
    PageInfo<WxCommodityDTO> getAllCommodity(WxCommodityDTO wxCommodityDTO, PageDTO pageDTO);

    Boolean deletedWxCommodity(String commodityId);

    Boolean addWxCommodity(WxCommodityDTO wxCommodityDTO, MultipartFile file, User user) throws IOException;

    Boolean addWxCommodityImg(WxCommodityDTO wxCommodityDTO, MultipartFile file, User user) throws IOException;

    Boolean updateCommodity(WxCommodityDTO wxCommodityDTO);

    Boolean deleteWxCommodityImg(String fileId,String id);

    List<Category> selectAllCategory();
}
