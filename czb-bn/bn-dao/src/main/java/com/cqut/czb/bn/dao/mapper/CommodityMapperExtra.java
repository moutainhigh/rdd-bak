package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.Commodity.AllCommodityDTO;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.Commodity.UserCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.Commodity.NavDTO;
import com.cqut.czb.bn.entity.dto.Commodity.ServiceDetailsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommodityMapperExtra {

    List<CommodityDTO> selectCommoditys(@Param("classification") String classification);

    List<UserCommodityOrderDTO> getCommodityOrderList(@Param("userId")String userId,@Param("state") Integer state);
    ServiceDetailsDTO selectServiceDetails(@Param("commodityId") String commodityId);

    List<AllCommodityDTO> selectAllCommodity(@Param("classification") String classification);

    List<NavDTO> selectShopSettleNav();

    List<CommodityDTO> selectCommodityByShop(CommodityDTO commodityDTO);

}