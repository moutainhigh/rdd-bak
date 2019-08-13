package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.appCarWash.ServiceCommodityDTO;

import java.util.List;

public interface AppCarWashService {
    /**
     * 查找服务
     * @return
     */
    List<ServiceCommodityDTO> SelectService();
}
