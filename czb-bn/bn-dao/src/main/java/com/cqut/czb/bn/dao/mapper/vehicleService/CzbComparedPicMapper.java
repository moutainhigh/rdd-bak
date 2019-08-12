package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CzbComparedPic;

public interface CzbComparedPicMapper {
    int deleteByPrimaryKey(String comparedPicId);

    int insert(CzbComparedPic record);

    int insertSelective(CzbComparedPic record);

    CzbComparedPic selectByPrimaryKey(String comparedPicId);

    int updateByPrimaryKeySelective(CzbComparedPic record);

    int updateByPrimaryKey(CzbComparedPic record);
}