package com.cqut.czb.bn.service.impl.equityPaymentServiceImpl;

import com.cqut.czb.bn.dao.mapper.equityPayment.EquityPaymentAreaClothingMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentAreaClothingDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.equityPaymentService.AreaClothingService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AreaClothingServiceImpl implements AreaClothingService {

    @Autowired
    EquityPaymentAreaClothingMapperExtra equityPaymentAreaClothingMapperExtra;

    @Override
    public PageInfo<EquityPaymentAreaClothingDTO> getAreaClothingList(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(), true);
        List<EquityPaymentAreaClothingDTO> equityPaymentAreaClothingDTOS =  equityPaymentAreaClothingMapperExtra.getAreaClothingList(equityPaymentAreaClothingDTO);
        return new PageInfo<>(equityPaymentAreaClothingDTOS);
    }

    @Override
    public JSONResult insertAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO) {
        equityPaymentAreaClothingDTO.setAreaId(StringUtil.createId());
        equityPaymentAreaClothingDTO.setUpdateAt(new Date());
        equityPaymentAreaClothingDTO.setCreateAt(new Date());
        return new JSONResult(equityPaymentAreaClothingMapperExtra.insertAreaClothing(equityPaymentAreaClothingDTO));
    }

    @Override
    public JSONResult updateAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO) {
        equityPaymentAreaClothingDTO.setUpdateAt(new Date());
        return new JSONResult(equityPaymentAreaClothingMapperExtra.updateAreaClothing(equityPaymentAreaClothingDTO));
    }

    @Override
    public JSONResult deleteAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO) {
        return new JSONResult(equityPaymentAreaClothingMapperExtra.deleteAreaClothing(equityPaymentAreaClothingDTO));
    }
}
