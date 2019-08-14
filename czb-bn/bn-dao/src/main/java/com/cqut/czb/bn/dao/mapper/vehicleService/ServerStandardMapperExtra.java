package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.appCarWash.ServiceCommodityDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServerStandardMapperExtra {

    List<ServiceCommodityDTO> selectServiceInfo();

    ServiceCommodityDTO selectOneService(@Param("serverId") String serverId);

}