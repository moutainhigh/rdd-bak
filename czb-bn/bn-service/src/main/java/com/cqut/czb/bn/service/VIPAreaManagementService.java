package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.VipArea.VipAreaDTO;
import com.cqut.czb.bn.entity.dto.VipArea.VipChangeType;
import com.cqut.czb.bn.entity.dto.VipArea.VipPriceAndNote;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-18 14:37
 */
public interface VIPAreaManagementService {
    PageInfo getVipAreaList(VipAreaDTO vipAreaDTO);

    Boolean addVipArea(VipAreaDTO vipAreaDTO);

    JSONResult closeAll(VipChangeType type,Integer isSpecial);


    Boolean editVipArea(VipAreaConfig vipAreaConfig);

    Boolean deleteVipArea(String vipAreaId);

    VipPriceAndNote getVipPriceAndNote(String area,Integer isSpecial);
}