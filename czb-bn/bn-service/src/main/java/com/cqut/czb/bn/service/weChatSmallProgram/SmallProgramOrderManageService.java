package com.cqut.czb.bn.service.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.DealCommodityInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderProcess;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
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
    JSONResult<PageInfo<WeChatCommodityOrderDTO>> getTableList(WeChatCommodityOrderDTO input, PageDTO page);

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
     * @param userId
     * @param orderId
     * @return
     */
    JSONResult<WeChatCommodityOrderDetail> getOrderDetail(String userId, String orderId);

    /**
     * 订单处理信息获取
     *
     * @param orderId
     * @return
     */
    JSONResult<WeChatCommodityOrderProcess> getOrderProcessInfo(String orderId);

    /**
     * 处理订单
     *
     * @param input
     * @return
     */
    JSONResult<Boolean> dealOrder(String userAccount, WeChatCommodityOrderProcess input);
}
