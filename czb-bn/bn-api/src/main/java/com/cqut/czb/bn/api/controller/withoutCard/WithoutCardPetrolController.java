package com.cqut.czb.bn.api.controller.withoutCard;

import com.cqut.czb.bn.entity.dto.DataWithCountOutputDTO;
import com.cqut.czb.bn.entity.dto.withoutCard.PetrolWithoutCardDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.withoutCard.WithoutCardPetrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者： 陈爽
 * 模块： 无卡加油油卡管理
 * 创建时间： 2020/11/17
 */

@RestController
@RequestMapping("/api/withoutCardPetrol")
public class WithoutCardPetrolController {

    @Autowired
    WithoutCardPetrolService withoutCardPetrolService;

    /**
     * 获取列表数据
     * @param petrolWithoutCardDto
     * @return
     */
    @PostMapping("/listByPagePetrol")
    public JSONResult listByPagePetrol(PetrolWithoutCardDto petrolWithoutCardDto) {
        DataWithCountOutputDTO dataWithCountOutputDTO = new DataWithCountOutputDTO();
        dataWithCountOutputDTO.setData(withoutCardPetrolService.listByPagePetrol(petrolWithoutCardDto));
        // 总消费金额
        dataWithCountOutputDTO.setCount(withoutCardPetrolService.getPetrolTotal(petrolWithoutCardDto));
        return new JSONResult(dataWithCountOutputDTO);
    }

    /**
     * 新增油卡
     * @param petrolWithoutCardDto
     * @return
     */
    @PostMapping("/insetPetrol")
    public JSONResult insetPetrol(PetrolWithoutCardDto petrolWithoutCardDto){
        return withoutCardPetrolService.insetPetrol(petrolWithoutCardDto);
    }

    /**
     * 删除油卡
     * @param petrolIds
     * @return
     */
    @PostMapping("/removePetrol")
    public JSONResult removePetrol(String petrolIds){
        return withoutCardPetrolService.removePetrol(petrolIds);
    }
}
