package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.shop.ShopDTO;

/**
 * @ClassName: BecomeBusinessmanMapperExtra
 * @Author: Iriya720
 * @Date: 2019/12/12
 * @Description:
 * @version: v1.0
 */
public interface BecomeBusinessmanMapperExtra {

    /**
     * 添加商家
     * @param shopDTO
     * @return
     */
    int insertShop(ShopDTO shopDTO);
}
