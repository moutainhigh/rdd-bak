package com.cqut.czb.bn.service.impl.directChargingSystem;

import com.cqut.czb.bn.dao.mapper.directChargingSystem.DirectChargingOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingPartnerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectChargingPartnerServiceImpl implements DirectChargingPartnerService {
    @Autowired
    DirectChargingOrderMapperExtra directChargingOrderMapperExtra;

    @Override
    public PageInfo<DirectChargingOrderDto> getDirectChargingPartnerList(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize());
        List<DirectChargingOrderDto> directChargingOrderDtoList = directChargingOrderMapperExtra.getDirectChargingPartnerList(directChargingOrderDto);
        return new PageInfo<>(directChargingOrderDtoList);
    }

    @Override
    public PageInfo getDirectChargingPartnerOrder(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize());
        List<DirectChargingOrderDto> directChargingOrderDtoList = directChargingOrderMapperExtra.getDirectChargingPartnerOrder(directChargingOrderDto);
        return new PageInfo<>(directChargingOrderDtoList);
    }

    @Override
    public JSONResult getTotalRechargeAmount() {
        return new JSONResult(directChargingOrderMapperExtra.getTotalRechargeAmount());
    }


}
