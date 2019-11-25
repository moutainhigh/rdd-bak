package com.cqut.czb.bn.api.controller.wechatApplet;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.wechatAppletService.WxCommodityManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/wxCommodityManage")
@RestController
public class wxCommodityManageController {

    @Autowired
    WxCommodityManageService wxCommodityManageService;

    @GetMapping("getAllCommodity")
    public JSONResult getAllCommodity(WxCommodityDTO wxCommodityDTO, PageDTO pageDTO){
        return new JSONResult(wxCommodityManageService.getAllCommodity(wxCommodityDTO,pageDTO));
    }

}
