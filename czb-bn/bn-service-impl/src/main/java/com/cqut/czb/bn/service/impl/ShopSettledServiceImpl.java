package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.CommodityMapperExtra;
import com.cqut.czb.bn.dao.mapper.FileFunctionMapperExtra;
import com.cqut.czb.bn.dao.mapper.ShopMapperExtra;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.shop.FileFunctionDTO;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.ShopSettledService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShopSettledServiceImpl implements ShopSettledService {

    @Autowired
    ShopMapperExtra shopMapperExtra;

    @Autowired
    FileFunctionMapperExtra fileFunctionMapperExtra;

    @Autowired
    CommodityMapperExtra commodityMapperExtra;


    @Override
    public ShopDTO getShopInfo(ShopDTO shopDTO, User user) {
        if (shopDTO.getUserId()==null||"".equals(shopDTO.getUserId())){
            shopDTO.setUserId(user.getUserId());
        }
        ShopDTO shop = shopMapperExtra.selectShop(shopDTO);
        if (shop!=null) {
            List<FileFunctionDTO> file = fileFunctionMapperExtra.selectFile(shopDTO);
            shop.setFile(file);
        }
        return shop;

    }

    @Override
    public Boolean updateShopInfo(ShopDTO shopDTO) {
        return shopMapperExtra.updateShopInfo(shopDTO)>0;
    }

    @Override
    public PageInfo<CommodityDTO> getCommodity(CommodityDTO commodityDTO, PageDTO pageDTO, User user) {
        if (commodityDTO.getShopId()==null||"".equals(commodityDTO.getShopId())){

        }
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<CommodityDTO> commodityDTOS = commodityMapperExtra.selectCommodityByShop(commodityDTO);
        return new PageInfo<>(commodityDTOS);
    }
}
