package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.vehicleService.CleanRiderMapper;
import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
import com.cqut.czb.bn.service.vehicleService.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lyk
 * @date: 8/12/2019
 */
@Service
public class RiderServiceImpl implements RiderService {

    @Autowired
    CleanRiderMapper cleanRiderMapper;

    @Override
    public int deleteByPrimaryKey(String riderId) {
        return 0;
    }

    @Override
    public List<CleanRider> selectAllRiders() {
        return cleanRiderMapper.selectAllRiders();
    }

    @Override
    public int insert(CleanRider record) {
        cleanRiderMapper.insert(record);
        return 0;
    }

    @Override
    public int insertSelective(CleanRider record) {
        cleanRiderMapper.insert(record);
        return 0;
    }

    @Override
    public CleanRider selectByPrimaryKey(String riderId) {
        return cleanRiderMapper.selectByPrimaryKey(riderId);
    }

    @Override
    public int updateByPrimaryKeySelective(CleanRider record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(CleanRider record) {
        return 0;
    }
}
