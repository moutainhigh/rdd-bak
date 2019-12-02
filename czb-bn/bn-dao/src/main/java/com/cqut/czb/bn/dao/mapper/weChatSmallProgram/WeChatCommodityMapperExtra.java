package com.cqut.czb.bn.dao.mapper.weChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOutputDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeChatCommodityMapperExtra {
    /**
     * 用于后台管理系统
     * @param wxCommodityDTO
     * @return
     */
    List<WxCommodityDTO> selectAllCommodity(WxCommodityDTO wxCommodityDTO);

    int deleteByPrimaryKey(String commodityId);

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

    Integer insertCommodity(WxCommodityDTO wxCommodityDTO);

    Integer updateCommodity(WxCommodityDTO wxCommodityDTO);

    /**
     * 获取开启服务地区列表
     * @return
     */
    String getAreas();

    Integer updateIsSale(@Param("ids") String ids, @Param("isSale") Integer isSale);
}
