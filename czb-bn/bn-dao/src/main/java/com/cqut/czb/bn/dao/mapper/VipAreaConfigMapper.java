package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.VipAreaConfig;

public interface VipAreaConfigMapper {
    int deleteByPrimaryKey(String vipAreaConfigId);

    int insert(VipAreaConfig record);

    int insertSelective(VipAreaConfig record);

    VipAreaConfig selectByPrimaryKey(String vipAreaConfigId);

    int updateByPrimaryKeySelective(VipAreaConfig record);

    int updateByPrimaryKey(VipAreaConfig record);
}