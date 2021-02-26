package com.cqut.czb.bn.dao.mapper.equityPayment;

import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentTypeDTO;

public interface EquityPaymentTypeMapperExtra {
    int insertType(EquityPaymentTypeDTO equityPaymentTypeDTO);

    int updateType(EquityPaymentTypeDTO equityPaymentTypeDTO);

    int deleteType(EquityPaymentTypeDTO equityPaymentTypeDTO);
}
