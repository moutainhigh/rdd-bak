package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Api;

public interface ApiMapper {
    int deleteByPrimaryKey(String apiId);

    int insert(Api record);

    int insertSelective(Api record);

    Api selectByPrimaryKey(String apiId);

    int updateByPrimaryKeySelective(Api record);

    int updateByPrimaryKey(Api record);
}