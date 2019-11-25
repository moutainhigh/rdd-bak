package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOutputDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;

import java.util.List;

public interface WeChatCommodityMapperExtra {
    /**
     * 用于后台管理系统
     * @param wxCommodityDTO
     * @return
     */
    List<WxCommodityDTO> selectAllCommodity(WxCommodityDTO wxCommodityDTO);

    /**
     * 用于微信小程序
     * @param wcpCommodityInputDTO
     * @return
     */
    List<WCPCommodityOutputDTO> selectAllCommodityByArea(WCPCommodityInputDTO wcpCommodityInputDTO);

    /**
     * 通过商品ID获取信息
     * @param commodityId
     * @return
     */
    WCPCommodityOutputDTO selectCommodityById(String commodityId);
}
