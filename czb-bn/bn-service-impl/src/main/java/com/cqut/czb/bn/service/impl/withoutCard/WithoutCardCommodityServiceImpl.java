package com.cqut.czb.bn.service.impl.withoutCard;

import com.cqut.czb.bn.dao.mapper.withoutCard.WithoutCardCommodityMapperExtra;
import com.cqut.czb.bn.entity.dto.withoutCard.CommodityWithoutCardDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.withoutCard.WithoutCardCommodityService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithoutCardCommodityServiceImpl implements WithoutCardCommodityService {
    @Autowired
    WithoutCardCommodityMapperExtra withoutCardCommodityMapperExtra;

    @Override
    public PageInfo<CommodityWithoutCardDto> listPetrolCommodity(CommodityWithoutCardDto commodityWithoutCardDto) {
        PageHelper.startPage(commodityWithoutCardDto.getCurrentPage(),commodityWithoutCardDto.getPageSize());
        return new PageInfo<>(withoutCardCommodityMapperExtra.listPetrolCommodity(commodityWithoutCardDto));
    }

    @Override
    public JSONResult insetCommodity(CommodityWithoutCardDto commodityWithoutCardDto) {
        commodityWithoutCardDto.setCommodityId(StringUtil.createId());
        return withoutCardCommodityMapperExtra.insetCommodity(commodityWithoutCardDto) > 0 ? new JSONResult(200,"新增成功") : new JSONResult(500,"新增失败");
    }

    @Override
    public JSONResult removeCommodity(String commodityId) {
        String[] ids = commodityId.split(",");
        return withoutCardCommodityMapperExtra.removeCommodity(ids) > 0 ? new JSONResult(200,"删除成功") : new JSONResult(500,"删除失败");
    }

    @Override
    public JSONResult updateCommodity(CommodityWithoutCardDto commodityWithoutCardDto) {
        return withoutCardCommodityMapperExtra.updateCommodity(commodityWithoutCardDto) > 0 ? new JSONResult(200,"更新成功") : new JSONResult(500,"更新失败");
    }

    @Override
    public CommodityWithoutCardDto getCommodityById(String commodityId) {
        return withoutCardCommodityMapperExtra.getCommodityById(commodityId);
    }

    @Override
    public JSONResult updateCommodityState(CommodityWithoutCardDto commodityWithoutCardDto) {
        String[] ids = commodityWithoutCardDto.getCommodityId().split(",");
        return withoutCardCommodityMapperExtra.updateCommodityState(ids,commodityWithoutCardDto.getState()) > 0 ? new JSONResult(200,"成功") : new JSONResult(500,"失败");
    }
}
