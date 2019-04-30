package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.AppRouter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface AppRouterMapper {
    Boolean deleteByPrimaryKey(String routerId);

    Boolean insert(AppRouter record);

    Boolean insertSelective(AppRouter record);

    List<AppRouter> selectByPrimaryKey(AppRouter appRouter);

    Boolean updateByPrimaryKeySelective(AppRouter record);

    Boolean updateByPrimaryKey(AppRouter record);
}