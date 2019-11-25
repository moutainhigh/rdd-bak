package com.cqut.czb.bn.service.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface SmallProgramOrderManageService {
    /**
     * 订单分页与查询
     *
     * @return
     */
    JSONResult<PageInfo<WeChatCommodityOrderDTO>>  getTableList(WeChatCommodityOrderDTO input, PageDTO page);

    /**
     * 作废订单
     *
     * @param orderId
     * @return
     */
    JSONResult<Boolean> obsoleteOrder(String orderId);

    /**
     * 订单详情
     *
     * @param orderId
     * @return
     */
    JSONResult<WeChatCommodityOrderDetail> getOrderDetail(String orderId);
}
