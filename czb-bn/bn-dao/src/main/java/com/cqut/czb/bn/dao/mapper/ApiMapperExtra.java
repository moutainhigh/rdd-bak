package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Api;

import java.util.List;

public interface ApiMapperExtra {
    int deleteByPrimaryKey(String apiId);

    int insert(Api record);

    int insertSelective(Api record);

    Api selectByPrimaryKey(String apiId);

    int updateByPrimaryKeySelective(Api record);

    int updateByPrimaryKey(Api record);

    List<Api> selectAllInfo();
}