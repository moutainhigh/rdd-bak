package com.cqut.czb.bn.api.controller.withoutCard;

import com.cqut.czb.bn.service.withoutCard.WithoutCardAreaService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
