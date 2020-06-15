package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeChatStockMapperExtra {

    /**
     * 从库存提出库存id
     * @param payInputDTO
     * @return
     */
    List<WeChatStock> getStockId(PayInputDTO payInputDTO);

    /**
     * 获取库存数量
     * @param payInputDTO
     * @return
     */
    int getStockNum(PayInputDTO payInputDTO);

    /**
     * 支付起调修改状态
     * @param ids
     * @return
     */
    int updateStock( List<WeChatStock> ids);

    List<String> selectElectronicCode(String userId);

    int insertSelective(WeChatCommodityOrder weChatCommodityOrder);

    /**
     * 支付成功修改状态
     * @param ids
     * @return
     */
    int updateStockState(List<WeChatStock> ids);

    /**
     * 查询是否支付成功
     * @param ids
     * @return
     */
    List<WeChatStock> selectStockState(List<WeChatStock> ids);

    /**
     * 修改成库存
     * @param ids
     * @return
     */
    int update(List<WeChatStock> ids);
}
