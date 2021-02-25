package com.cqut.czb.bn.dao.mapper.equityPayment;

import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;

import java.util.List;

public interface EquityPaymentRecordMapperExtra {

    List<EquityPaymentDTO> getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO);
}
