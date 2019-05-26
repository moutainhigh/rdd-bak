package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
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
    public JSONResult selectDictList(DictInputDTO dictInputDTO, PageDTO pageDTO){

        return new JSONResult(dictService.selectDictList(dictInputDTO, pageDTO));
    }

    @RequestMapping(value = "/updateDict",method = RequestMethod.POST)
    public JSONResult updateDict(DictInputDTO dictInputDTO){

        return new JSONResult(dictService.updateDict(dictInputDTO));
    }

    @RequestMapping(value = "/deleteDict",method = RequestMethod.POST)
    public JSONResult deleteDict(DictInputDTO dictInputDTO){

        return new JSONResult(dictService.deleteDict(dictInputDTO));
    }

    @RequestMapping(value = "/insertDict",method = RequestMethod.POST)
    public JSONResult insertDict(DictInputDTO dictInputDTO){

        return new JSONResult(dictService.insertDict(dictInputDTO));
    }

    @RequestMapping(value = "/getDicByName",method = RequestMethod.GET)
    public JSONResult getDicByName(DictInputDTO dictInputDTO){
        return new JSONResult(dictService.getDicByName(dictInputDTO));
    }
}
