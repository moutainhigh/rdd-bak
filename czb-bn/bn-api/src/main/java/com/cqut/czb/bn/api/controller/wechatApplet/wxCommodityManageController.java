package com.cqut.czb.bn.api.controller.wechatApplet;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxAttributeDTO;
import com.cqut.czb.bn.entity.dto.wechatAppletCommodity.WxCommodityDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.wechatAppletService.WxCommodityManageService;
import org.apache.ibatis.annotations.Param;
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
    public JSONResult getAllCommodity(Principal principal, WxCommodityDTO wxCommodityDTO, PageDTO pageDTO){
        if (principal==null || principal.getName()==null){
            return new JSONResult(500,"token为空");
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(wxCommodityManageService.getAllCommodity(wxCommodityDTO,pageDTO, user.getUserId()));
    }

    /**
     * 获取商品属性信息
     * @param WxAttributeDTO
     * @param pageDTO
     * @return
     */
    @GetMapping("/getAllWxAttribute")
    public JSONResult getAllWxAttribute(Principal principal, WxAttributeDTO WxAttributeDTO, PageDTO pageDTO){
        if (principal==null || principal.getName()==null){
            return new JSONResult(500,"token为空");
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(wxCommodityManageService.selectAllWxAttribute(WxAttributeDTO, pageDTO, user));
    }

    @PermissionCheck(role = "管理员")
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
    public JSONResult addWxCommodity(WxCommodityDTO wxCommodityDTO, @RequestParam("file")MultipartFile file, Principal principal) throws IOException, InterruptedException {
        if (principal==null || principal.getName()==null){
            return new JSONResult(500,"token为空");
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(wxCommodityManageService.addWxCommodity(wxCommodityDTO, file, user));
    }

    @PostMapping("/addWxCommodityImg")
    public JSONResult addWxCommodityImg(WxCommodityDTO wxCommodityDTO, @RequestParam("file")MultipartFile file, Principal principal) throws IOException {
        if (principal==null || principal.getName()==null){
            return new JSONResult(500,"token为空");
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(wxCommodityManageService.addWxCommodityImg(wxCommodityDTO, file, user));
    }

    /**
     * 新增商品属性
     * @param wxAttributeDTO
     * @param principal
     * @return
     * @throws IOException
     */
    @PermissionCheck(role = "管理员")
    @PostMapping("/addWxAttribute")
    public JSONResult addWxAttribute(WxAttributeDTO wxAttributeDTO, @RequestParam("file")MultipartFile file, Principal principal) throws IOException, InterruptedException {
        if (principal==null || principal.getName()==null){
            return new JSONResult(500,"token为空");
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(wxCommodityManageService.addWxAttribute(wxAttributeDTO, file, user));
    }

    @PostMapping("/updateWxCommodity")
    public JSONResult updateWxCommodityInfo(@RequestBody WxCommodityDTO wxCommodityDTO){
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

    @PostMapping("/editCommodityWithImg")
    public JSONResult editCommodityWithImg(Principal principal, WxCommodityDTO wxCommodityDTO, @RequestParam("file")MultipartFile file) throws IOException {
        if (principal==null || principal.getName()==null){
            return new JSONResult(500,"token为空");
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(wxCommodityManageService.editWeChatCommodityWithImg(user.getUserId(), wxCommodityDTO, file));
    }

    /**
     * 后台管理系统 开停售功能
     * @param ids
     * @param type 1:开售 2:停售
     * @return
     */
    @PostMapping("/haltSales")
    public JSONResult haltSalesById(String ids, Integer type){
        return new JSONResult(wxCommodityManageService.haltOrOnSales(ids, type));
    }

    @GetMapping("/getAllShopInfo")
    public JSONResult getAllShopInfo(){
        return new JSONResult(wxCommodityManageService.getAllShopInfo());
    }

    @PermissionCheck(role = "管理员")
    @PostMapping("/updateWxAttribute")
    public JSONResult updateWxAttribute(WxAttributeDTO wxAttributeDTO, @RequestParam("file")MultipartFile file, Principal principal) throws IOException {
        if (principal==null || principal.getName()==null){
            return new JSONResult(500,"token为空");
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(wxCommodityManageService.updateWxAttribute(wxAttributeDTO, file, user));
    }

    @PermissionCheck(role = "管理员")
    @PostMapping("/updateWxAttributeFile")
    public JSONResult updateWxAttributeFile(WxAttributeDTO wxAttributeDTO, Principal principal) throws IOException {
        if (principal==null || principal.getName()==null){
            return new JSONResult(500,"token为空");
        }
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(wxCommodityManageService.updateWxAttributeFile(wxAttributeDTO, user));
    }
    @PermissionCheck(role = "管理员")
    @GetMapping("/getAttributeName")
    public JSONResult getAttributeName(){
        return new JSONResult(wxCommodityManageService.getAttributeName());
    }
    @PermissionCheck(role = "管理员")
    @GetMapping("/getAttributeContent")
    public JSONResult getAttributeContent(String name){
        return new JSONResult(wxCommodityManageService.getAttributeContent(name));
    }

    /**
     * 删除商品属性
     * @param wxAttributeDTO
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PermissionCheck(role = "管理员")
    @PostMapping("/deleteWxAttribute")
    public JSONResult deleteWxAttribute(WxAttributeDTO wxAttributeDTO) throws IOException, InterruptedException {
        return new JSONResult(wxCommodityManageService.deleteWxAttribute(wxAttributeDTO));
    }

    /**
     * 查询
     * @param wxAttributeDTO
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PermissionCheck(role = "管理员")
    @PostMapping("/checkWxAttribute")
    public JSONResult checkWxAttribute(WxAttributeDTO wxAttributeDTO)throws IOException, InterruptedException {
        return new JSONResult(wxCommodityManageService.checkWxAttribute(wxAttributeDTO));
    }
}
