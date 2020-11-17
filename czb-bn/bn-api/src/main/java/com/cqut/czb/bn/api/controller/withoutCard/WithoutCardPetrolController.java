package com.cqut.czb.bn.api.controller.withoutCard;

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

}
