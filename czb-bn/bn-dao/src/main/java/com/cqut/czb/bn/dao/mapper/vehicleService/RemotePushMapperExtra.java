package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.RemotePush;

public interface RemotePushMapperExtra {

    int insert(RemotePush remotePush);

    RemotePush selectByUser(String userId);
}
