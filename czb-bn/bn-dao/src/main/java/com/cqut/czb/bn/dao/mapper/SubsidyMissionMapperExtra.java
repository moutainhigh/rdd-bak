package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.CreateSubsidies.CreateSubsidiesQueryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubsidyMissionMapperExtra {

    List<CreateSubsidiesQueryDTO> getPartnerSubordinates(@Param("createSubsidiesQueryDTO") CreateSubsidiesQueryDTO createSubsidiesQueryDTO);
}
