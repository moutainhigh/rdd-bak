package com.cqut.czb.bn.dao.mapper.appWithoutCard;

import com.cqut.czb.bn.entity.dto.Commodity.UserCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.entity.withoutCard.CommodityWithoutCard;
import com.cqut.czb.bn.entity.entity.withoutCard.WithoutCardAreaConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppWithoutCardMapperExtra {

    List<WithoutCardAreaConfig> selectConfig(@Param(value = "area") String area);

    List<CommodityWithoutCard> getGoods();

    List<UserCommodityOrderDTO> getCommodityOrderList(@Param("userId")String userId, @Param("isRecharged") Integer isRecharged);
}
