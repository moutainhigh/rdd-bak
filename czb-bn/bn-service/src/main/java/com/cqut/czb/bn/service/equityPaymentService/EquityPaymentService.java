package com.cqut.czb.bn.service.equityPaymentService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.CategoryAndTypeDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentCommodityDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/25
 */
public interface EquityPaymentService {

    /**
     * 获取商品列表
     * @param equityPaymentCommodityDTO
     * @param pageDTO
     * @return
     */
    PageInfo<EquityPaymentCommodityDTO> getCommodityList(EquityPaymentCommodityDTO equityPaymentCommodityDTO, PageDTO pageDTO);
    /**
     * 获取商品订单详情
     * @return
     */
    PageInfo<EquityPaymentDTO> getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO, PageDTO pageDTO);

    /**
     * 级联选择-类目-类别
     * @return
     */
    List<CategoryAndTypeDTO> getCategoryAndType();

}
