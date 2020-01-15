package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOutputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-25 15:00
 */
public interface WCPCommodityInfoService {
    List<WCPCommodityOutputDTO> getCommodity(WCPCommodityInputDTO wcpCommodityInputDTO);

    WCPCommodityOutputDTO getOneCommodityById(WCPCommodityInputDTO wcpCommodityInputDTO);

    List<String> getAreas();

    List<WCPCommodityOutputDTO> getClassification(WCPCommodityOutputDTO wcpCommodityOutputDTO);

    List<String> getContent();

    JSONResult getCommodityTitle();
}