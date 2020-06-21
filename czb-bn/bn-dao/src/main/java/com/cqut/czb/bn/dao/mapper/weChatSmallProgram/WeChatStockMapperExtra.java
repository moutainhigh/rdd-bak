package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatStock;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WeChatStockMapperExtra {

    /**
     * 从库存提出库存id
     * @param params
     * @return
     */
    List<WeChatStock> getStockId(Map<String, Object> params);

    /**
     * 获取库存数量4
     * @param params
     * @return
     */
    Integer getStockNum(Map<String, Object> params);

    /**
     * 支付起调修改状态
     * @param ids
     * @return
     */
    Integer updateStock( List<WeChatStock> ids);

    List<String> selectElectronicCode(@Param("ids") String ids);

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
    Integer selectStockState(List<WeChatStock> ids);

    /**
     * 修改成库存
     * @param ids
     * @return
     */
    int update(List<WeChatStock> ids);

    Integer getLimitNum(@Param("commodityId") String commodityId,@Param("userId") String userId);


}
