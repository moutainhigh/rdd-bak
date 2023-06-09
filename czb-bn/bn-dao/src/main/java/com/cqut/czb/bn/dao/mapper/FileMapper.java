package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.File;

public interface FileMapper {
    int deleteByPrimaryKey(String fileId);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(String fileId);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);
}