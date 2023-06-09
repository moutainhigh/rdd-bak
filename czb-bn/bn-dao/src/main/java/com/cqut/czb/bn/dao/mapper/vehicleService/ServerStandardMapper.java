package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Component;

@Component
public interface ServerStandardMapper {
    int deleteByPrimaryKey(String serverId);

    int insert(ServerStandard record);

    int insertSelective(ServerStandard record);

    ServerStandard selectByPrimaryKey(String serverId);

    int updateByPrimaryKeySelective(ServerStandard record);

    int updateByPrimaryKey(ServerStandard record);

    Page<ServerStandard> search(ServerStandard serverStandard);
}