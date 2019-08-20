package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CleanRiderMapperExtra {
    boolean deleteByPrimaryKey(String riderId);

    List<CleanRider> selectAllRiders();

    List<CleanRider> selectByStatus(Integer status);

    List<CleanRider> selectByName(String riderName);

    boolean insert(CleanRider record);

    boolean insertSelective(CleanRider record);

    CleanRider selectByPrimaryKey(String riderId);

    boolean updateByPrimaryKeySelective(CleanRider record);

    boolean updateByPrimaryKey(CleanRider record);
}