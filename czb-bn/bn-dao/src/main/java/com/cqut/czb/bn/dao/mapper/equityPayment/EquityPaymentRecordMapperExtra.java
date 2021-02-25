package com.cqut.czb.bn.dao.mapper.equityPayment;

import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.List;

public interface EquityPaymentRecordMapperExtra {

    List<EquityPaymentDTO> getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO);

    JSONResult getCategoryAndType();
}
