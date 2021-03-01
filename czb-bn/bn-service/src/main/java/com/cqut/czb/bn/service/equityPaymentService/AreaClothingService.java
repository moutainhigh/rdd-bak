package com.cqut.czb.bn.service.equityPaymentService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentAreaClothingDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

public interface AreaClothingService {
    /**
     * 获取区服信息列表
     * @return
     */
    PageInfo<EquityPaymentAreaClothingDTO> getAreaClothingList(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO, PageDTO pageDTO);

    JSONResult insertAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO);

    JSONResult updateAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO);

    JSONResult deleteAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO);
}
