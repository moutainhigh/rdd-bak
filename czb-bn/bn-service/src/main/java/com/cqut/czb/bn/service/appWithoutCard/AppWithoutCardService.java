package com.cqut.czb.bn.service.appWithoutCard;

import com.cqut.czb.bn.entity.dto.Commodity.UserCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.withoutCard.CommodityWithoutCard;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AppWithoutCardService {

    List<PetrolZoneDTO> getGoods(User user, String area);

    List<UserCommodityOrderDTO> getCommodityOrderList(String userId, Integer state);


}
