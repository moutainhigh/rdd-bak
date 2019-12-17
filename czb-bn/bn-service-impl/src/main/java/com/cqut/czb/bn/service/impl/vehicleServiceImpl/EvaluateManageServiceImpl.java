package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.vehicleService.RiderEvaluateMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.RiderEvaluateDTO;
import com.cqut.czb.bn.service.vehicleService.EvaluateManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluateManageServiceImpl implements EvaluateManageService{

    @Autowired
    RiderEvaluateMapperExtra riderEvaluateMapperExtra;

    @Override
    public PageInfo<RiderEvaluateDTO> getEvaluateList(RiderEvaluateDTO riderEvaluateDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(riderEvaluateMapperExtra.selectRiderEvaluateList(riderEvaluateDTO)) ;
    }
}
