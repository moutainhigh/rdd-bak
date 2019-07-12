package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.PartnerConsumptionMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.service.PartnerConsumptionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerConsumptionServiceImpl implements PartnerConsumptionService {
    @Autowired
    PartnerConsumptionMapperExtra partnerConsumptionMapperExtra;

    @Override
    public PageInfo<PartnerDTO> getConsumptionList(PartnerDTO partnerDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<PartnerDTO> partnerDTOList =  partnerConsumptionMapperExtra.selectConsumptionList(partnerDTO);
        return new PageInfo<>(partnerDTOList);
    }

    @Override
    public PageInfo<PartnerDTO> getDetailConsumption(PartnerDTO partnerDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(partnerConsumptionMapperExtra.selectDetailConsumption(partnerDTO));
    }


}
