package com.cqut.czb.bn.api.controller.withoutCard;

import com.cqut.czb.bn.entity.dto.withoutCard.WithoutCardAreaConfigDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.withoutCard.WithoutCardAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者： 陈爽
 * 模块： 无卡加油区域管理
 * 创建时间： 2020/11/17
 */

@RestController
@RequestMapping("/api/withoutArea")
public class WithoutCardAreaController {

    @Autowired
    WithoutCardAreaService withoutCardAreaService;

    @PostMapping("/listWithoutCardArea")
    public JSONResult listWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto){
        return new JSONResult(withoutCardAreaService.listWithoutCardArea(withoutCardAreaConfigDto));
    }

    @PostMapping("/insertWithoutCardArea")
    public JSONResult insertWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto){
        return withoutCardAreaService.insertWithoutCardArea(withoutCardAreaConfigDto);
    }

    @PostMapping("/removetWithoutCardArea")
    public JSONResult removetWithoutCardArea(String petrolConfigId){
        return withoutCardAreaService.removetWithoutCardArea(petrolConfigId);
    }

    @PostMapping("/updatetWithoutCardArea")
    public JSONResult updatetWithoutCardArea(WithoutCardAreaConfigDto withoutCardAreaConfigDto){
        return withoutCardAreaService.updatetWithoutCardArea(withoutCardAreaConfigDto);
    }

    @PostMapping("/gettWithoutCardAreaById")
    public JSONResult gettWithoutCardAreaById(String petrolConfigId){
        return new JSONResult(withoutCardAreaService.gettWithoutCardAreaById(petrolConfigId));
    }
}
