package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.dao.mapper.directChargingSystem.PrepaidRefillMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingCommodityDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.UserCardRelationDto;
import com.cqut.czb.bn.entity.entity.directChargingSystem.DirectChargingCommodity;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrepaidRefillServiceImpl implements PrepaidRefillService {
    @Autowired
    PrepaidRefillMapperExtra prepaidRefillMapperExtra;

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
}
