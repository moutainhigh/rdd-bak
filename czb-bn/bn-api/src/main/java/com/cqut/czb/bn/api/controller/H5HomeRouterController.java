package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.dao.mapper.UserMapper;
import com.cqut.czb.bn.entity.dto.IOSHomeTabDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AnnouncementService;
import com.cqut.czb.bn.service.AppHomePageService;
import com.cqut.czb.bn.service.IDictService;
import com.cqut.czb.bn.service.vehicleService.RiderService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import io.swagger.models.Model;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：陈德强
 * 作用：手机app公告管理，对应公告表：announcement
 * 创建时间：2019/4/20
 */
@RestController
@RequestMapping("/H5HomePage")
public class H5HomeRouterController {

    @Autowired
    AppHomePageService appHomePageService;

    @Autowired
    IDictService iDictService;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RiderService riderService;

    @Autowired
    private AnnouncementService announcementService;


    /**
     * 首页菜单路由获取
     */
    @RequestMapping(value ="/selectHomePageRouters",method = RequestMethod.GET)
    public JSONResult selectHomePageRouters( AppRouterDTO appRouterDTO){
        return new JSONResult(appHomePageService.selectHomePageRouters(appRouterDTO));
    }


    /**
     * 获取图片路径
     */
    @GetMapping("/getPicPath")
    public JSONResult getPicPath(@RequestParam("id") String id){
        return new JSONResult(announcementService.getFileById(id));
    }

}
