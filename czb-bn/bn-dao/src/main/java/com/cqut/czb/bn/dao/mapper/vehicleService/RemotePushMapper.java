package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.RemotePush;

public interface RemotePushMapper {
    int deleteByPrimaryKey(String deviceId);

    int insert(RemotePush record);

    int insertSelective(RemotePush record);

    RemotePush selectByPrimaryKey(String deviceId);

    int updateByPrimaryKeySelective(RemotePush record);

    int updateByPrimaryKey(RemotePush record);
}