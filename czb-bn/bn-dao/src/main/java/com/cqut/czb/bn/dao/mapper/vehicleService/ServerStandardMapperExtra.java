package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.dto.appCarWash.ServiceCommodityDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerStandardMapperExtra {

    List<ServiceCommodityDTO> selectServiceInfo();

    ServiceCommodityDTO selectOneService(@Param("serverId") String serverId);

}