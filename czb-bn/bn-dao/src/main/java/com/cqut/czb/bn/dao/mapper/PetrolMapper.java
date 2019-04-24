package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.entity.Petrol;

import java.util.List;

public interface PetrolMapper {
    int deleteByPrimaryKey(String petrolId);

    int insert(Petrol record);

    int insertSelective(Petrol record);

    Petrol selectByPrimaryKey(String petrolId);

    int updateByPrimaryKeySelective(Petrol record);

    int updateByPrimaryKey(Petrol record);

    /**
     * 获取未售出的油卡信息
     * @return
     */
    List<PetrolZoneDTO> selectPetrolZone();
}