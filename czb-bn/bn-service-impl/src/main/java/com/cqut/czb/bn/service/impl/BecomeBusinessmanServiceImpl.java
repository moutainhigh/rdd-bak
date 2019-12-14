package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.BecomeBusinessmanMapperExtra;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.BecomeBusinessmanService;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: BecomeBusinessmanServiceImpl
 * @Author: Iriya720
 * @Date: 2019/12/12
 * @Description:
 * @version: v1.0
 */
@Service
public class BecomeBusinessmanServiceImpl implements BecomeBusinessmanService {

    @Autowired
    private BecomeBusinessmanMapperExtra mapper;

    /**
     * 添加商家
     * @param shopDTO
     * @return
     */
    @Override
    public int addBusinessman(ShopDTO shopDTO, User user) {
        shopDTO.setUserId(user.getUserId());
        String shopId = StringUtil.createId();
        shopDTO.setShopId(shopId);
        return mapper.insertShop(shopDTO);
    }
}
