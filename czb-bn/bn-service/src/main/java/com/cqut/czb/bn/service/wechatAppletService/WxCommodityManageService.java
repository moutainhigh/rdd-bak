package com.cqut.czb.bn.service.wechatAppletService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.ShopInfoDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.Category;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface WxCommodityManageService {
    PageInfo<WxCommodityDTO> getAllCommodity(WxCommodityDTO wxCommodityDTO, PageDTO pageDTO, String userId);

    Boolean deletedWxCommodity(String commodityId);

    Boolean addWxCommodity(WxCommodityDTO wxCommodityDTO, MultipartFile file, User user) throws IOException, InterruptedException;

    Boolean addWxCommodityImg(WxCommodityDTO wxCommodityDTO, MultipartFile file, User user) throws IOException;

    Boolean updateCommodity(WxCommodityDTO wxCommodityDTO);

    Boolean deleteWxCommodityImg(String fileId,String id);

    List<Category> selectAllCategory();

    Boolean haltOrOnSales(String ids, Integer type);

    Boolean editWeChatCommodityWithImg(String userId, WxCommodityDTO WxCommodityDTO, MultipartFile file) throws IOException;

    List<ShopInfoDTO> getAllShopInfo();
}
