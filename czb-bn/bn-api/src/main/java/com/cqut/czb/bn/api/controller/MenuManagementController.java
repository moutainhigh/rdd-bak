package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.menu.MenuIdDTO;
import com.cqut.czb.bn.entity.dto.menu.MenuInputDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IMenuService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * MenuManagementController 菜单管理接口
 * 设计者:   曹渝
 * 更新日期: 2019/04/21
 */
@RestController
@RequestMapping("/api/menuManagement")
public class MenuManagementController {

    @Autowired
    IMenuService menuService;

    @RequestMapping(value = "/insertMenu",method = RequestMethod.POST)
    public JSONResult insertMenu(@Validated @RequestBody MenuInputDTO menuInputDTO){
        boolean isInsert = menuService.insertMenu(menuInputDTO);
        if(isInsert) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "新增成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "新增失败");
        }
    }

    @RequestMapping(value = "/deleteMenu",method = RequestMethod.POST)
    public JSONResult deleteMenu(@Validated @RequestBody MenuIdDTO menuIdDTO){

        boolean isDelete = menuService.deleteMenu(menuIdDTO);
        if(isDelete) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "删除成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "删除失败");
        }
    }

    @RequestMapping(value = "/updateMenu",method = RequestMethod.POST)
    public JSONResult updateMenu(@Validated @RequestBody MenuInputDTO menuInputDTO){
        boolean isUpdate = menuService.updateMenu(menuInputDTO);
        if(isUpdate) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "更新成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "更新失败");
        }
    }

    @RequestMapping(value = "/selectMenu",method = RequestMethod.GET)
    public JSONResult selectMenu(@Validated MenuInputDTO menuInputDTO, PageDTO pageDTO){

        return new JSONResult(menuService.selectMenu(menuInputDTO, pageDTO));
    }
}
