package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.entity.food.dishOrder;

public interface dishOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(dishOrder record);

    int insertSelective(dishOrder record);

    dishOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(dishOrder record);

    int updateByPrimaryKey(dishOrder record);
}