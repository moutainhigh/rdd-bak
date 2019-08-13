package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CleanRiderMapper {
    int deleteByPrimaryKey(String riderId);

    List<CleanRider> selectAllRiders();

    int insert(CleanRider record);

    int insertSelective(CleanRider record);

    CleanRider selectByPrimaryKey(String riderId);

    int updateByPrimaryKeySelective(CleanRider record);

    int updateByPrimaryKey(CleanRider record);
}