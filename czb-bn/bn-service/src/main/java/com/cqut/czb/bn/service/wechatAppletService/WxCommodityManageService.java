package com.cqut.czb.bn.service.wechatAppletService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.ShopInfoDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxAttributeDTO;
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

    String getAttributeId(WxAttributeDTO wxAttributeDTO);

    Boolean updateCommodity(WxCommodityDTO wxCommodityDTO);

    Boolean deleteWxCommodityImg(String fileId,String id);

    List<Category> selectAllCategory();


    PageInfo<WxAttributeDTO> selectAllWxAttribute(WxAttributeDTO WxAttributeDTO, PageDTO pageDTO, User user);

    Boolean haltOrOnSales(String ids, Integer type);

    Boolean editWeChatCommodityWithImg(String userId, WxCommodityDTO WxCommodityDTO, MultipartFile file) throws IOException;

    List<ShopInfoDTO> getAllShopInfo();

    Boolean addWxAttribute(WxAttributeDTO wxAttributeDTO, MultipartFile file, User user) throws InterruptedException, IOException;

    Boolean updateWxAttribute(WxAttributeDTO wxAttributeDTO, MultipartFile file, User user) throws IOException;

    Boolean updateWxAttributeFile(WxAttributeDTO wxAttributeDTO, User user) throws IOException;

    List<String> getAttributeName();

    List<String> getAttributeContent(String name);


    Boolean deleteWxAttribute(WxAttributeDTO wxAttributeDTO);

    List<WxAttributeDTO> checkWxAttribute(WxAttributeDTO wxAttributeDTO);
}
