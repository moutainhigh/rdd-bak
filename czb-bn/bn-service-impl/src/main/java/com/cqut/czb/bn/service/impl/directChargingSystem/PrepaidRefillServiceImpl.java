package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.PrepaidRefillMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingCommodityDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.UserCardRelationDto;
import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingCommodity;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jodd.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrepaidRefillServiceImpl implements PrepaidRefillService {
    @Autowired
    PrepaidRefillMapperExtra prepaidRefillMapperExtra;

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Override
    public List<DirectChargingCommodity> getGoodsPrice(Integer type) {
        return prepaidRefillMapperExtra.getGoodsPrice(type);
    }

    @Override
    public PageInfo<DirectChargingCommodity> getAllCommodity(DirectChargingCommodity directChargingCommodity, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(prepaidRefillMapperExtra.selectAllCommodity(directChargingCommodity));
    }

    @Override
    public Boolean addCommodity(DirectChargingCommodity directChargingCommodity) {
        directChargingCommodity.setCommodityId(StringUtil.createId());
        return prepaidRefillMapperExtra.addCommodity(directChargingCommodity)>0;
    }

    @Override
    public Boolean updateCommodity(DirectChargingCommodityDto directChargingCommodityDto) {
        return prepaidRefillMapperExtra.updateCommodity(directChargingCommodityDto) > 0;
    }

    @Override
    public UserCardRelationDto getInfoNum(String userId) {
        return prepaidRefillMapperExtra.getInfoNum(userId);
    }

    @Override
    public JSONResult deletedCommodity(String commodityId) {
        boolean isDelete = prepaidRefillMapperExtra.deletedCommodity(commodityId) > 0;
        return isDelete ? new JSONResult(200) : new JSONResult(500);
    }

    @Override
    public JSONResult saleStatusCommodity(String commodityIds, Integer state) {
        String[] ids = commodityIds.split(",");
        boolean isSuccess = prepaidRefillMapperExtra.saleStatusCommodity(ids,state) > 0;
        return isSuccess ? new JSONResult(200) : new JSONResult(500);
    }

    @Override
    public JSONResult updateDirectRecharge(int status) {
        DictInputDTO dictInputDTO = new DictInputDTO();
        dictInputDTO.setName("is_direct_recharge");
        dictInputDTO.setContent(Util.toString(status));
        int column = dictMapperExtra.updateDictByName(dictInputDTO);
        if (column == 0) {
            return new JSONResult("更新失败");
        } else {
            return new JSONResult("更新成功");
        }
    }

    @Override
    public JSONResult getDirectRecharge() {
        try {
            return new JSONResult(Integer.parseInt(dictMapperExtra.selectDictByName("is_direct_recharge").getContent()));
        } catch (Exception e) {
            return new JSONResult("直充状态出错");
        }
    }
}
