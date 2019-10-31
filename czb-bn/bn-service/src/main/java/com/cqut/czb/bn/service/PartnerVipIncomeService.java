package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.PartnerVipIncomeDTO;
import com.cqut.czb.bn.entity.entity.PartnerVipIncome;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PartnerVipIncomeService {

    PageInfo<PartnerVipIncomeDTO> getVipIncomeList(PartnerVipIncomeDTO partnerVipIncomeDTO, PageDTO pageDTO);

    Boolean settleVipIncome(PartnerVipIncomeDTO partnerVipIncomeDTO);

    Boolean addVipIncome(String userId,Double addIncome, Integer type);

    PartnerVipIncomeDTO getVipIncomeByUser(User user);

    Boolean initVipIncomeData();

}
