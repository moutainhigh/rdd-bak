package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.CreateSubsidies.CreateSubsidiesOutputDTO;
import com.cqut.czb.bn.entity.dto.CreateSubsidies.CreateSubsidiesQueryDTO;

public interface CreateSubsidiesService {

    public CreateSubsidiesOutputDTO getPartnerSubordinates(CreateSubsidiesQueryDTO createSubsidiesQueryDTO);
}
