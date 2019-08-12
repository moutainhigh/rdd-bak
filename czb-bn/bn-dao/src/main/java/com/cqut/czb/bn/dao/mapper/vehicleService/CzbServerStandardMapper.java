package com.cqut.czb.bn.dao.mapper.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CzbServerStandard;

public interface CzbServerStandardMapper {
    int deleteByPrimaryKey(String serverId);

    int insert(CzbServerStandard record);

    int insertSelective(CzbServerStandard record);

    CzbServerStandard selectByPrimaryKey(String serverId);

    int updateByPrimaryKeySelective(CzbServerStandard record);

    int updateByPrimaryKey(CzbServerStandard record);
}