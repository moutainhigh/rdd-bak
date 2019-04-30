package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.ContractModel;

public interface ContractModelMapper {
    int deleteByPrimaryKey(String modelId);

    int insert(ContractModel record);

    int insertSelective(ContractModel record);

    ContractModel selectByPrimaryKey(String modelId);

    int updateByPrimaryKeySelective(ContractModel record);

    int updateByPrimaryKey(ContractModel record);
}