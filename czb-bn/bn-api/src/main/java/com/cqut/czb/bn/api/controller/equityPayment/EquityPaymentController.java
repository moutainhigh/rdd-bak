package com.cqut.czb.bn.api.controller.equityPayment;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentCommodityDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.equityPaymentService.EquityPaymentService;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者： 袁菘壑 侯家领
 * 积分系统 integral
 * 创建时间： 2021/2/25
 */

@RestController
@RequestMapping("/api/equityPayment")
public class EquityPaymentController {

    @Autowired
    EquityPaymentService equityPaymentService;

    @RequestMapping(value = "/getCommodityView", method = RequestMethod.GET)
    public JSONResult getCommodityView() {
        return null;
    }

    /**
     * 获取商品订单详情
     * @return
     */
    @RequestMapping(value = "/getEquityPaymentRecord", method = RequestMethod.GET)
    public JSONResult getEquityPaymentRecord(EquityPaymentDTO equityPaymentDTO, PageDTO pageDTO) {
        return new JSONResult(equityPaymentService.getEquityPaymentRecord(equityPaymentDTO, pageDTO));
    }

    /**
     * 级联选择-类目-类别
     * @return
     */
    @RequestMapping(value = "/getCategoryAndType", method = RequestMethod.GET)
    public JSONResult getCategoryAndType() {
        return new JSONResult(equityPaymentService.getCategoryAndType());
    }

    /**
     * 分页查询权益商品
     * @return
     */
    @RequestMapping(value = "/getEquityPaymentByPage", method = RequestMethod.GET)
    public JSONResult getEquityPaymentByPage(EquityPaymentCommodityDTO equityPaymentCommodityDTO, PageDTO pageDTO) {
        return new JSONResult(equityPaymentService.getEquityPaymentByPage(equityPaymentCommodityDTO, pageDTO));
    }
}
