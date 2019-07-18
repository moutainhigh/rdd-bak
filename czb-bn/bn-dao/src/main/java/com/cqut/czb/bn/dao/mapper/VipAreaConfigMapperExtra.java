package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.VipArea.VipAreaDTO;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-18 19:23
 */
public interface VipAreaConfigMapperExtra {
    List<VipAreaDTO> getVipAreaList(VipAreaDTO vipAreaDTO);

    int addVipArea(VipAreaDTO vipAreaDTO);
}