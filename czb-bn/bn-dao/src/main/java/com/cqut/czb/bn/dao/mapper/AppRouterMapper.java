package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.AppRouter;

public interface AppRouterMapper {
    int deleteByPrimaryKey(String routerId);

    int insert(AppRouter record);

    int insertSelective(AppRouter record);

    AppRouter selectByPrimaryKey(String routerId);

    int updateByPrimaryKeySelective(AppRouter record);

    int updateByPrimaryKey(AppRouter record);
}