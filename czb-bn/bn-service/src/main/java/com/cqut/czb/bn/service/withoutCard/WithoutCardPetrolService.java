package com.cqut.czb.bn.service.withoutCard;

import com.cqut.czb.bn.entity.dto.withoutCard.PetrolWithoutCardDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

/**
 * 作者： 陈爽
 * 模块： 无卡加油油卡管理
 * 创建时间： 2020/11/17
 */
@Component
public interface WithoutCardPetrolService {

    PageInfo<PetrolWithoutCardDto> listByPagePetrol(PetrolWithoutCardDto petrolWithoutCardDto);

    String getPetrolTotal(PetrolWithoutCardDto petrolWithoutCardDto);

    JSONResult insetPetrol(PetrolWithoutCardDto petrolWithoutCardDto);

    JSONResult removePetrol(String petrolIds);
}
