package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerStandardMapper {
    int deleteByPrimaryKey(String serverId);

    int insert(ServerStandard record);

    int insertSelective(ServerStandard record);

    ServerStandard selectByPrimaryKey(String serverId);

    int updateByPrimaryKeySelective(ServerStandard record);

    int updateByPrimaryKey(ServerStandard record);
}