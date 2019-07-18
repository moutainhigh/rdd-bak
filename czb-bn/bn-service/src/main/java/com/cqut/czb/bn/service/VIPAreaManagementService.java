package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.VipArea.VipAreaDTO;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.github.pagehelper.PageInfo;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-18 14:37
 */
public interface VIPAreaManagementService {
    PageInfo getVipAreaList(VipAreaDTO vipAreaDTO);

    Boolean addVipArea(VipAreaDTO vipAreaDTO);

    Boolean editVipArea(VipAreaConfig vipAreaConfig);
}