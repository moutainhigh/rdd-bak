package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CzbCleanRider;

public interface CzbCleanRiderMapper {
    int deleteByPrimaryKey(String riderId);

    int insert(CzbCleanRider record);

    int insertSelective(CzbCleanRider record);

    CzbCleanRider selectByPrimaryKey(String riderId);

    int updateByPrimaryKeySelective(CzbCleanRider record);

    int updateByPrimaryKey(CzbCleanRider record);
}