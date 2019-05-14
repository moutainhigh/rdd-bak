package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictMapperExtra {

    List<Dict> selectDict(String name);

    Dict selectDictByName(@Param("name") String name);

    int updateDict(DictInputDTO dictInputDTO);

    int deleteDict(String dictId);

    int insertDict(DictInputDTO dictInputDTO);

}