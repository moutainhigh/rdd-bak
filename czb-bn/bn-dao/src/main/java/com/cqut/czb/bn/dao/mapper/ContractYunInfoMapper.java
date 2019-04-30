package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.ContractYunInfo;

public interface ContractYunInfoMapper {
    int deleteByPrimaryKey(String yunContractId);

    int insert(ContractYunInfo record);

    int insertSelective(ContractYunInfo record);

    ContractYunInfo selectByPrimaryKey(String yunContractId);

    int updateByPrimaryKeySelective(ContractYunInfo record);

    int updateByPrimaryKey(ContractYunInfo record);
}