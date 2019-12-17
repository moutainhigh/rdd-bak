package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.VipRechargeRecords;
import org.springframework.stereotype.Repository;

@Repository
public interface VipRechargeRecordsMapper {
    int deleteByPrimaryKey(String recordId);

    int insert(VipRechargeRecords record);

    int insertSelective(VipRechargeRecords record);

    VipRechargeRecords selectByPrimaryKey(String recordId);

    int updateByPrimaryKeySelective(VipRechargeRecords record);

    int updateByPrimaryKey(VipRechargeRecords record);
}