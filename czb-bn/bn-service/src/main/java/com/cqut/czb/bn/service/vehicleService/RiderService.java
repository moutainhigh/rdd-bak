package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import org.springframework.stereotype.Component;

/**
 * @author: lyk
 * @date: 8/12/2019
 */
@Component
public interface RiderService {
    int deleteByPrimaryKey(String riderId);

    int insert(CleanRider record);

    int insertSelective(CleanRider record);

    CleanRider selectByPrimaryKey(String riderId);

    int updateByPrimaryKeySelective(CleanRider record);

    int updateByPrimaryKey(CleanRider record);
}
