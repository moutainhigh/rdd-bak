package com.cqut.czb.bn.api.controller.food;

import com.alibaba.fastjson.JSON;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.food.AppPicService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/appService")
public class AppServiceController {

    @Autowired
    AppPicService appPicService;

    @GetMapping("getPic")
    public JSONResult getPic(AppRouterDTO appRouterDTO, String code, String userId, String area, HashMap<String,T> name){
        return new JSONResult(appPicService.getPic(appRouterDTO,code,userId,area,name));
    }
}
