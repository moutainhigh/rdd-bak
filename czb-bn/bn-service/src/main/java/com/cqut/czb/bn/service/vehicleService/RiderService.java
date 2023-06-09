package com.cqut.czb.bn.service.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: lyk
 * @date: 8/12/2019
 */
@Component
public interface RiderService {
    boolean deleteByPrimaryKey(String riderId);

    List<CleanRider> selectAllRiders();

    List<CleanRider> selectByStatus(Integer status);

    List<CleanRider> selectByName(String riderName);

    PageInfo<CleanRider> getRider(Integer pageSize, Integer currentPage, CleanRider record);

    boolean insert(CleanRider record);

    boolean insertSelective(CleanRider record);

    CleanRider selectByPrimaryKey(String riderId);

    boolean updateByPrimaryKeySelective(CleanRider record);

    boolean updateByPrimaryKey(CleanRider record);

    void sendMesToApp(String noticeId, String userId);
}
