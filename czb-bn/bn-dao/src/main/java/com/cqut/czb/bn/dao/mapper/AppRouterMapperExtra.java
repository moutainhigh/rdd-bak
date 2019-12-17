package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface AppRouterMapperExtra {

    int deleteByPrimaryKey(String routerId);

    int insert(AppRouter record);

    int insertSelective(AppRouter record);

    List<AppRouter> selectByPrimaryKey(AppRouter appRouter);

    int updateByPrimaryKeySelective(AppRouter record);

    int updateByPrimaryKey(AppRouter record);

    List<AppRouterDTO> selectAppRouters(AppRouterDTO appRouter);

    List<AppRouterDTO> selectAppRouterBycode(AppRouterDTO appRouterDTO);

    AppRouterDTO selectAppRouterById(String routerId);

}
