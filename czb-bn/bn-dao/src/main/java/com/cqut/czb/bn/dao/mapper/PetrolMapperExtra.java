package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;

import java.util.List;

public interface PetrolMapperExtra {
    List<Petrol> getPetrolList(GetPetrolListInputDTO inputDTO);
}