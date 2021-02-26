package com.cqut.czb.bn.dao.mapper.equityPayment;

import com.cqut.czb.bn.entity.dto.equityPayment.CategoryAndTypeDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquityPaymentRecordMapperExtra {

    List<EquityPaymentDTO> getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO);

    List<CategoryAndTypeDTO> getCategoryAndType(CategoryAndTypeDTO categoryAndTypeDTO);

    List<CategoryAndTypeDTO> getAllType(CategoryAndTypeDTO categoryAndTypeDTO);

    EquityPaymentDTO getEquityPaymentOrderDetails(@Param("orderId") String orderId);

    List<EquityPaymentDTO> getUserEquityPaymentOrders(String userId);
}
