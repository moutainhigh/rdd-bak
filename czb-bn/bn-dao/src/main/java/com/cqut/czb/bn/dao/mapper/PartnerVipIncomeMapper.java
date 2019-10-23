package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.PartnerVipIncome;

public interface PartnerVipIncomeMapper {
    int deleteByPrimaryKey(String partnerVipIncomeId);

    int insert(PartnerVipIncome record);

    int insertSelective(PartnerVipIncome record);

    PartnerVipIncome selectByPrimaryKey(String partnerVipIncomeId);

    int updateByPrimaryKeySelective(PartnerVipIncome record);

    int updateByPrimaryKey(PartnerVipIncome record);
}