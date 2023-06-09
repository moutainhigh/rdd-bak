package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.partnerVipIncome.PartnerVipIncomeDTO;
import com.cqut.czb.bn.entity.entity.PartnerVipIncome;

import java.util.List;

public interface PartnerVipIncomeMapperExtra {
    List<PartnerVipIncomeDTO> selectVipIncomeList(PartnerVipIncomeDTO partnerVipIncomeDTO);

    PartnerVipIncome selectVipIncomeById(String partnerVipIncomeId);

    PartnerVipIncomeDTO selectVipIncomeByPartnerId(String partnerId);

    List<PartnerVipIncomeDTO> selectVipIncomeByIds(String[] array);

    int settleVipIncomeRecords(String[] array);

    int updateVipIncomeByAdd(PartnerVipIncome partnerVipIncome);

    int insertIncome(PartnerVipIncome partnerVipIncome);

    int updateByPrimaryKeySelective(PartnerVipIncome partnerVipIncome);
}
