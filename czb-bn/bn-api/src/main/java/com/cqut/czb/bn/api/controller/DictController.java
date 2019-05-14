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

    @RequestMapping(value = "/selectAndroidInfo",method = RequestMethod.GET)
    public JSONResult selectAndroidInfo(){

        return new JSONResult(dictService.selectAndroidInfo());
    }

    @RequestMapping(value = "/selectIOSInfo",method = RequestMethod.GET)
    public JSONResult selectIOSInfo(){

        return new JSONResult(dictService.selectIOSInfo());
    }

    @RequestMapping(value = "/selectDictList",method = RequestMethod.GET)
    public JSONResult selectDictList(){

        return new JSONResult(dictService.selectDictList());
    }

    @RequestMapping(value = "/updateDict",method = RequestMethod.POST)
    public JSONResult updateDict(){

        return new JSONResult(dictService.selectCustomerServiceStaff());
    }

    @RequestMapping(value = "/deleteDict",method = RequestMethod.POST)
    public JSONResult deleteDict(){

        return new JSONResult(dictService.selectCustomerServiceStaff());
    }

    @RequestMapping(value = "/insertDict",method = RequestMethod.POST)
    public JSONResult insertDict(){

        return new JSONResult(dictService.selectCustomerServiceStaff());
    }
}
