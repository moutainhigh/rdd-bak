package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.EnterpriseInfo;

public interface EnterpriseInfoMapper {
    int deleteByPrimaryKey(String enterpriseInfoId);

    int insert(EnterpriseInfo record);

    int insertSelective(EnterpriseInfo record);

    EnterpriseInfo selectByPrimaryKey(String enterpriseInfoId);

    int updateByPrimaryKeySelective(EnterpriseInfo record);

    int updateByPrimaryKey(EnterpriseInfo record);
}