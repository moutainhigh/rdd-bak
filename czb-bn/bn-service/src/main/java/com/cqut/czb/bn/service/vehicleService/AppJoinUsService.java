package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import org.springframework.stereotype.Service;

@Service
public interface AppJoinUsService {
    Boolean toBecomeRider(CleanRider rider, User user);

    Boolean toBecomeShop(ShopDTO shopDTO,User user);
}
