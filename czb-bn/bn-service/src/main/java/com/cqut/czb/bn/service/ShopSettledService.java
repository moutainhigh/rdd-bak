package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityUserInfoCollectionDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ShopSettledService {
    ShopDTO getShopInfo(ShopDTO shopDTO, User user);

    Boolean updateShopInfo(ShopDTO shopDTO,MultipartFile file);

    Boolean updateShopInfoNoFile(ShopDTO shopDTO );

    PageInfo<CommodityDTO> getCommodity(CommodityDTO commdityDTO, PageDTO pageDTO, User user);

    Boolean insertInfo(CommodityUserInfoCollectionDTO[] commodityUserInfoCollectionDTO);

    List<CommodityUserInfoCollectionDTO> selectInfo(CommodityUserInfoCollectionDTO[] commodityUserInfoCollectionDTO);

    List<CommodityUserInfoCollectionDTO> selectInfoByShop(CommodityUserInfoCollectionDTO commodityUserInfoCollectionDTO);

    Boolean updateInfo(List<CommodityUserInfoCollectionDTO> commodityUserInfoCollectionDTO);


    Boolean deleteInfo(String[] commodityUserInfoCollectionDTO);
    Boolean insertCommodity (CommodityDTO commodityDTO, MultipartFile file,User user);

    Boolean updateCommodity (CommodityDTO commodityDTO, MultipartFile file);

    Boolean updateCommodityNoFile(CommodityDTO commodityDTO);
}
