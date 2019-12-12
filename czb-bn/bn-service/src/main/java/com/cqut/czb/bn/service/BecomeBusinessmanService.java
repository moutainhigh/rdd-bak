package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.User;

/**
 * @ClassName: BecomeBusinessmanService
 * @Author: Iriya720
 * @Date: 2019/12/12
 * @Description:
 * @version: v1.0
 */
public interface BecomeBusinessmanService {
    int addBusinessman(ShopDTO shopDTO, User user);
}
