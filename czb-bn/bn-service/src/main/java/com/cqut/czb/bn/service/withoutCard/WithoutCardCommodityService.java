package com.cqut.czb.bn.service.withoutCard;


import com.cqut.czb.bn.entity.dto.WxVipMoenyDTO;
import com.cqut.czb.bn.entity.dto.withoutCard.CommodityWithoutCardDto;
import com.cqut.czb.bn.entity.entity.VIPApply;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatVipApply;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * 作者： 陈爽
 * 模块： 无卡加油商品管理
 * 创建时间： 2020/11/17
 */
@Component
public interface WithoutCardCommodityService {

    PageInfo<CommodityWithoutCardDto> listPetrolCommodity(CommodityWithoutCardDto commodityWithoutCardDto);

    JSONResult insetCommodity(CommodityWithoutCardDto commodityWithoutCardDto);

    JSONResult removeCommodity(String commodityId);

    JSONResult updateCommodity(CommodityWithoutCardDto commodityWithoutCardDto);

    CommodityWithoutCardDto getCommodityById(String commodityId);

    JSONResult updateCommodityState(CommodityWithoutCardDto commodityWithoutCardDto);
}
