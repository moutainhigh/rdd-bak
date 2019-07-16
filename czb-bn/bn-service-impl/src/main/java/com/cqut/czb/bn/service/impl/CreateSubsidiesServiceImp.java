package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.SubsidyMissionMapperExtra;
import com.cqut.czb.bn.entity.dto.CreateSubsidies.CreateSubsidiesOutputDTO;
import com.cqut.czb.bn.entity.dto.CreateSubsidies.CreateSubsidiesQueryDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.service.CreateSubsidiesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CreateSubsidiesServiceImp implements CreateSubsidiesService {

    @Autowired
    SubsidyMissionMapperExtra subsidyMissionMapperExtra;

    @Override
    public CreateSubsidiesOutputDTO getPartnerSubordinates(CreateSubsidiesQueryDTO createSubsidiesQueryDTO) {
        CreateSubsidiesOutputDTO createSubsidiesOutputDTO = new CreateSubsidiesOutputDTO();
        PageHelper.startPage(createSubsidiesQueryDTO.getCurrentPage(),createSubsidiesQueryDTO.getPageSize());
        createSubsidiesOutputDTO.setCreateSubsidiesQueryDTOList(new PageInfo<>(subsidyMissionMapperExtra.getPartnerSubordinates(createSubsidiesQueryDTO)));
        double totalSubsidies = 0.0;
        for(CreateSubsidiesQueryDTO partnerSubordinate : createSubsidiesOutputDTO.getCreateSubsidiesQueryDTOList().getList()){
            if(partnerSubordinate.getSubsidies() != null){
                totalSubsidies += partnerSubordinate.getSubsidies();
            }
        }
        createSubsidiesOutputDTO.setTotalSubsidies(totalSubsidies);
        return createSubsidiesOutputDTO;
    }
}
