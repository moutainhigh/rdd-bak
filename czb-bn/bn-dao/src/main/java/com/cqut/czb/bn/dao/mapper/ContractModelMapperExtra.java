package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.ContractModel;

import java.util.List;

public interface ContractModelMapperExtra {

    int insertContractModel(ContractModel record);

    List<ContractModel> selectContractModelList();
}