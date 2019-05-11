package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dict")
public class DictController {

    @Autowired
    IDictService dictService;

    @RequestMapping(value = "/selectCustomerServiceStaff",method = RequestMethod.GET)
    public JSONResult selectCustomerServiceStaff(){

        return new JSONResult(dictService.selectCustomerServiceStaff());
    }
}
