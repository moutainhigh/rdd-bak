package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.VipArea.VipAreaDTO;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-18 19:23
 */
@Repository
public interface VipAreaConfigMapperExtra {
    List<VipAreaDTO> getVipAreaList(VipAreaDTO vipAreaDTO);

    int addVipArea(VipAreaDTO vipAreaDTO);

    VipAreaConfig selectVipPrice(@Param("area") String area,@Param("isSpecial") Integer isSpecial);

    VipAreaConfig selectVipAreaConfigByArea(String area);

    VipAreaConfig selectVipPriceById(@Param("vipAreaConfigId") String vipAreaConfigId);

    int closeAll(@Param("list") List<VipAreaDTO> vipAreaDTOList);

    int openAll(@Param("list") List<VipAreaDTO> vipAreaDTOList);
}