package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.ComparedPic;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparedPicMapper {
    int deleteByPrimaryKey(String comparedPicId);

    int insert(ComparedPic record);

    int insertSelective(ComparedPic record);

    ComparedPic selectByPrimaryKey(String comparedPicId);

    int updateByPrimaryKeySelective(ComparedPic record);

    int updateByPrimaryKey(ComparedPic record);
}