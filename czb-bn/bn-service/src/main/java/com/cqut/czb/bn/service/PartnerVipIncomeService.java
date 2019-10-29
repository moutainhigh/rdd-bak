package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.PartnerVipIncomeDTO;
import com.cqut.czb.bn.entity.entity.PartnerVipIncome;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PartnerVipIncomeService {

    PageInfo<PartnerVipIncomeDTO> getVipIncomeList(PartnerVipIncomeDTO partnerVipIncomeDTO, PageDTO pageDTO);

    Boolean settleVipIncome(PartnerVipIncomeDTO partnerVipIncomeDTO);

    Boolean initVipIncomeData();

    Boolean addVipIncome(String userId,Double addIncome);

}
