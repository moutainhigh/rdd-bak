package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeChatCommodityOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(WeChatCommodityOrder record);

    int insertSelective(WeChatCommodityOrder record);

    WeChatCommodityOrder selectByPrimaryKey(String orderId);

    List<WeChatCommodityOrder> selectByCommodityId(@Param("commodityId") String commodityId, @Param("state") Integer state);

    int updateByPrimaryKeySelective(WeChatCommodityOrder record);

    int updateByPrimaryKey(WeChatCommodityOrder record);
}
