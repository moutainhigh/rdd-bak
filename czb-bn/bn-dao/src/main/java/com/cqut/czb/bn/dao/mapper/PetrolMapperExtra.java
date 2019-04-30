package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.appHomePage.PetrolZoneDTO;
import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;

import java.util.List;

public interface PetrolMapperExtra {
    List<Petrol> getPetrolList(GetPetrolListInputDTO inputDTO);
    int insertPetrolList(List<Petrol> list);

    /**
     * 获取未售出的油卡信息
     * @return
     */
    List<PetrolZoneDTO> selectPetrolZone();

    List<Petrol> selectPetrol();
}