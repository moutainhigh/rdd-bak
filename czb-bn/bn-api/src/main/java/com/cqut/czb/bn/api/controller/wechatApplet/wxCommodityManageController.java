package com.cqut.czb.bn.api.controller.wechatApplet;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.wechatAppletService.WxCommodityManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RequestMapping("/api/wxCommodityManage")
@RestController
public class wxCommodityManageController {
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    WxCommodityManageService wxCommodityManageService;

    @GetMapping("/getAllCommodity")
    public JSONResult getAllCommodity(WxCommodityDTO wxCommodityDTO, PageDTO pageDTO){
        return new JSONResult(wxCommodityManageService.getAllCommodity(wxCommodityDTO,pageDTO));
    }

    @PostMapping("/deletedWxCommodity")
    public JSONResult deletedWxCommodity(String commodityId){
        Boolean deleted = wxCommodityManageService.deletedWxCommodity(commodityId);
        if (deleted){
            return new JSONResult(200,deleted);
        }else {
            return new JSONResult(500,deleted);
        }

    }

    @PostMapping("/addWxCommodity")
    public JSONResult addWxCommodity(WxCommodityDTO wxCommodityDTO, @RequestParam("file")MultipartFile file, Principal principal) throws IOException {
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(wxCommodityManageService.addWxCommodity(wxCommodityDTO, file, user));
    }

    @PostMapping("/addWxCommodityImg")
    public JSONResult addWxCommodityImg(WxCommodityDTO wxCommodityDTO, @RequestParam("file")MultipartFile file, Principal principal) throws IOException {
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(wxCommodityManageService.addWxCommodityImg(wxCommodityDTO, file, user));
    }

    @PostMapping("/updateWxCommodity")
    public JSONResult updateWxCommodityInfo(WxCommodityDTO wxCommodityDTO){
        return new JSONResult(wxCommodityManageService.updateCommodity(wxCommodityDTO));
    }

    @PostMapping("/deleteWxCommodityImg")
    public JSONResult deleteWxCommodityImg(String fileId, String id){
        return new JSONResult(wxCommodityManageService.deleteWxCommodityImg(fileId, id));
    }

    @GetMapping("/getAllCommodityType")
    public JSONResult getAllCommodityType(){
        return new JSONResult(wxCommodityManageService.selectAllCategory());
    }

//    @PostMapping("/haltSales")
//    public JSONResult haltSalesById(String ids){
//
//    }
}
