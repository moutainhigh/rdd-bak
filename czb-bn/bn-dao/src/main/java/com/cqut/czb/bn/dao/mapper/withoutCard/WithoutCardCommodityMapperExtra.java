package com.cqut.czb.bn.dao.mapper.withoutCard;


import com.cqut.czb.bn.entity.dto.withoutCard.CommodityWithoutCardDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithoutCardCommodityMapperExtra {

    List<CommodityWithoutCardDto> listPetrolCommodity(CommodityWithoutCardDto commodityWithoutCardDto);

    int insetCommodity(CommodityWithoutCardDto commodityWithoutCardDto);

    int removeCommodity(@Param("ids")String[] ids);

    int updateCommodity(CommodityWithoutCardDto commodityWithoutCardDto);

    CommodityWithoutCardDto getCommodityById(@Param("commodityId")String commodityId);

    int updateCommodityState(@Param("ids")String[] ids,@Param("state")int state);
}
