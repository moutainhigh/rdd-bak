package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.dao.mapper.directChargingSystem.DirectChargingCommodityMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingCommodityDto;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingCommodityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectChargingCommodityServiceImpl implements DirectChargingCommodityService {
    @Autowired
    DirectChargingCommodityMapperExtra directChargingCommodityMapperExtra;

    @Override
    public PageInfo<DirectChargingCommodityDto> getAllCommodity(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        List<DirectChargingCommodityDto> directChargingCommodityDtoList = directChargingCommodityMapperExtra.getAllCommodity();
        return new PageInfo<>(directChargingCommodityDtoList);
    }
}
