package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.Commodity.CommodityUserInfoCollectionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommodityUserInfoCollectionMapperExtra {

    List<CommodityUserInfoCollectionDTO> selectInfoInput(@Param("commodityId") String commodityId);

    List<CommodityUserInfoCollectionDTO> selectInfoByShop(CommodityUserInfoCollectionDTO commodityUserInfoCollectionDTO);

    int insertInfo(CommodityUserInfoCollectionDTO[] array);

    int updateInfo(List<CommodityUserInfoCollectionDTO> list);

    int deleteInfo(String[] array);

    List<CommodityUserInfoCollectionDTO> selectInfo(CommodityUserInfoCollectionDTO[] commodityUserInfoCollectionDTO);

}