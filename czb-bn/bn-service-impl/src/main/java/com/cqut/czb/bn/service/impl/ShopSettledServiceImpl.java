package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.FileFunctionMapperExtra;
import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.dao.mapper.ShopMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.shop.FileFunctionDTO;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.FileFunction;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.ShopSettledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShopSettledServiceImpl implements ShopSettledService {

    @Autowired
    ShopMapperExtra shopMapperExtra;

    @Autowired
    FileFunctionMapperExtra fileFunctionMapperExtra;


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
}
