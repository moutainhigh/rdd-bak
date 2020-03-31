package com.cqut.czb.bn.dao.mapper.autoRecharge;

import com.cqut.czb.bn.entity.entity.autoRecharge.AutoRechargeRecord;

public interface AutoRechargeRecordMapper {
    int deleteByPrimaryKey(String autoRechargeRecordId);

    int insert(AutoRechargeRecord record);

    int insertSelective(AutoRechargeRecord record);

    AutoRechargeRecord selectByPrimaryKey(String autoRechargeRecordId);

    int updateByPrimaryKeySelective(AutoRechargeRecord record);

    int updateByPrimaryKey(AutoRechargeRecord record);
}