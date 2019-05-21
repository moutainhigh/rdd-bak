package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.EnterpriseConsultingInfo;

public interface EnterpriseConsultingInfoMapper {
    int deleteByPrimaryKey(String consultingId);

    int insert(EnterpriseConsultingInfo record);

    int insertSelective(EnterpriseConsultingInfo record);

    EnterpriseConsultingInfo selectByPrimaryKey(String consultingId);

    int updateByPrimaryKeySelective(EnterpriseConsultingInfo record);

    int updateByPrimaryKey(EnterpriseConsultingInfo record);
}