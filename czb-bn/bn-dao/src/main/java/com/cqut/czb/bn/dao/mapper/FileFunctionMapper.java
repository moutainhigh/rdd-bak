package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.FileFunction;

public interface FileFunctionMapper {
    int deleteByPrimaryKey(String id);

    int insert(FileFunction record);

    int insertSelective(FileFunction record);

    FileFunction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FileFunction record);

    int updateByPrimaryKey(FileFunction record);
}