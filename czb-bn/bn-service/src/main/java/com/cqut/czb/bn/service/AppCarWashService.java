package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;

import java.util.List;

public interface AppCarWashService {
    /**
     * 查找服务
     * @return
     */
    List<ServerStandard> SelectService();
}
