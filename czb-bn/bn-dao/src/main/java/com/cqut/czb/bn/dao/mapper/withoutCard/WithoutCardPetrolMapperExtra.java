package com.cqut.czb.bn.dao.mapper.withoutCard;

import com.cqut.czb.bn.entity.dto.withoutCard.PetrolWithoutCardDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WithoutCardPetrolMapperExtra {

    List<PetrolWithoutCardDto> listByPagePetrol(PetrolWithoutCardDto petrolWithoutCardDto);

    String getPetrolTotal(PetrolWithoutCardDto petrolWithoutCardDto);

    int insetPetrol(PetrolWithoutCardDto petrolWithoutCardDto);

    int removePetrol(@Param("ids")String[] ids);
}
