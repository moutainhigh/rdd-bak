package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Dict;

import java.util.List;

public interface DictMapperExtra {

    List<Dict> selectDict(String name);
}