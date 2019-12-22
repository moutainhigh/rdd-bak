package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppRouterManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**AppConfigManageController app配置管理模块
 *
 */
@RestController
@RequestMapping("/api/appRouter")
public class AppRouterManageController {

    @Autowired
    AppRouterManageService appRouterManageService;

    @Autowired
    RedisUtils redisUtils;
    //获取菜单数据&查询
    @GetMapping("/selectAppRouter")
    public JSONResult selectAppRouterList(AppRouter appRouter, PageDTO pageDTO){
        return new JSONResult(appRouterManageService.getMenuList(appRouter,pageDTO));
    }

    //修改数据+修改图标
    @PermissionCheck(role = "管理员")
    @PostMapping("/updateAppRouter")
    public JSONResult updateAppRouter(AppRouter appRouter, Principal principal,@RequestParam("file")MultipartFile file) throws  Exception{
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(appRouterManageService.updateMenu(appRouter,file,user));
    }

    //修改数据
    @PermissionCheck(role = "管理员")
    @PostMapping("/updateAppRouterNoFile")
    public JSONResult updateAppRouter(@RequestBody AppRouter appRouter){
        return new JSONResult(appRouterManageService.updateMenuNoFile(appRouter));
    }

    //删除
    @PermissionCheck(role = "管理员")
    @PostMapping("/deleteAppRouter")
    public JSONResult deleteAppRouter(@RequestBody AppRouter appRouter){
        return new JSONResult(appRouterManageService.deleteMenu(appRouter));
    }
    //新增
    @PermissionCheck(role = "管理员")
    @PostMapping("/insertAppRouter")
    public JSONResult insertAppRouter(AppRouter appRouter,Principal principal,@RequestParam("file")MultipartFile file) throws Exception{
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(appRouterManageService.insertMenu(appRouter,file,user));

    }
}
