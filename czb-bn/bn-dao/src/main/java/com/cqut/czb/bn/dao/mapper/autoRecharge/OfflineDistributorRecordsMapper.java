package com.cqut.czb.bn.dao.mapper.autoRecharge;

import com.cqut.czb.bn.entity.entity.autoRecharge.OfflineDistributorRecords;

public interface OfflineDistributorRecordsMapper {
    int deleteByPrimaryKey(String rechargeId);

    int insert(OfflineDistributorRecords record);

    int insertSelective(OfflineDistributorRecords record);

    OfflineDistributorRecords selectByPrimaryKey(String rechargeId);

    int updateByPrimaryKeySelective(OfflineDistributorRecords record);

    int updateByPrimaryKey(OfflineDistributorRecords record);
}