package com.cqut.czb.bn.service.impl.withoutCard;

import com.cqut.czb.bn.dao.mapper.withoutCard.WithoutCardPetrolMapperExtra;
import com.cqut.czb.bn.entity.dto.withoutCard.PetrolWithoutCardDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.withoutCard.WithoutCardPetrolService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cqut.czb.bn.util.string.StringUtil;

@Service
public class WithoutCardPetrolServiceImpl implements WithoutCardPetrolService {
    @Autowired
    WithoutCardPetrolMapperExtra withoutCardPetrolMapperExtra;

    @Override
    public PageInfo<PetrolWithoutCardDto> listByPagePetrol(PetrolWithoutCardDto petrolWithoutCardDto) {
        PageHelper.startPage(petrolWithoutCardDto.getCurrentPage(), petrolWithoutCardDto.getPageSize());
        return new PageInfo<>(withoutCardPetrolMapperExtra.listByPagePetrol(petrolWithoutCardDto));
    }

    @Override
    public String getPetrolTotal(PetrolWithoutCardDto petrolWithoutCardDto) {
        return withoutCardPetrolMapperExtra.getPetrolTotal(petrolWithoutCardDto);
    }

    @Override
    public JSONResult insetPetrol(PetrolWithoutCardDto petrolWithoutCardDto) {
        petrolWithoutCardDto.setPetrolId(StringUtil.createId());
        return withoutCardPetrolMapperExtra.insetPetrol(petrolWithoutCardDto) > 0 ? new JSONResult(200,"新增成功") : new JSONResult(500,"新增失败");
    }

    @Override
    public JSONResult removePetrol(String petrolIds) {
        String[] ids = petrolIds.split(",");
        return withoutCardPetrolMapperExtra.removePetrol(ids) > 0 ? new JSONResult(200,"删除成功") : new JSONResult(500,"删除失败");
    }
}
