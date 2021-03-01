package com.cqut.czb.bn.dao.mapper.equityPayment;

import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentCategoryDTO;

public interface EquityPaymentCategoryMapperExtra {
    int insertCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO);

    int updateCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO);

    int deleteCategory(EquityPaymentCategoryDTO equityPaymentCategoryDTO);

    int updateCategoryOrder(EquityPaymentCategoryDTO equityPaymentCategoryDTO);
}
