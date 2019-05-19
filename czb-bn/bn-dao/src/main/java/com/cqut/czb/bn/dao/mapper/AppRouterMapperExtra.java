package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;

import java.util.List;

public interface AppRouterMapperExtra {

    int deleteByPrimaryKey(String routerId);

    int insert(AppRouter record);

    int insertSelective(AppRouter record);

    List<AppRouter> selectByPrimaryKey(AppRouter appRouter);

    int updateByPrimaryKeySelective(AppRouter record);

    int updateByPrimaryKey(AppRouter record);

    List<AppRouterDTO> selectAppRouters(AppRouterDTO appRouter);

}
