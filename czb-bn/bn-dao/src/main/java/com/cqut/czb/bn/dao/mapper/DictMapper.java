package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Dict;

public interface DictMapper {
    int deleteByPrimaryKey(String dictId);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(String dictId);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);
}