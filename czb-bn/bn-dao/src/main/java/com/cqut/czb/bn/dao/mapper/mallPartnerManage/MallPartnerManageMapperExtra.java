package com.cqut.czb.bn.dao.mapper.mallPartnerManage;

import com.cqut.czb.bn.entity.dto.mallPartnerManage.ConsumptionDetailsDTO;
import com.cqut.czb.bn.entity.dto.mallPartnerManage.MallPartnerDTO;
import com.cqut.czb.bn.entity.entity.User;

import java.util.List;

public interface MallPartnerManageMapperExtra {
    double statisticsMoney();

    List<MallPartnerDTO> selectMallPartnerList(MallPartnerDTO mallPartnerDTO);

    List<ConsumptionDetailsDTO> selectMallPartnerConsumptionDetails(User user);
}
