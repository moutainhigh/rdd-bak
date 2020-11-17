package com.cqut.czb.bn.dao.mapper.withoutCard;

import com.cqut.czb.bn.entity.entity.withoutCard.WithoutCardAreaConfig;

public interface WithoutCardAreaConfigMapper {
    int deleteByPrimaryKey(String petrolConfigId);

    int insert(WithoutCardAreaConfig record);

    int insertSelective(WithoutCardAreaConfig record);

    WithoutCardAreaConfig selectByPrimaryKey(String petrolConfigId);

    int updateByPrimaryKeySelective(WithoutCardAreaConfig record);

    int updateByPrimaryKey(WithoutCardAreaConfig record);
}