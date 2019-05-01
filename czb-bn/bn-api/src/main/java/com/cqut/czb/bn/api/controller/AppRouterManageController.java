package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.AppRouter;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppRouterManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**AppConfigManageController app配置管理模块
 *
 */
@RestController
@RequestMapping("/appRouter")
public class AppRouterManageController {

    @Autowired
    AppRouterManageService appRouterManageService;

    @GetMapping("selectAppRouter")
    public JSONResult selectAppRouterList(AppRouter appRouter, PageDTO pageDTO){
        return new JSONResult(appRouterManageService.getMenuList(appRouter,pageDTO));
    }
    @PostMapping("updateAppRouter")
    public JSONResult updateAppRouter(@RequestBody AppRouter appRouter,MultipartFile file) throws  Exception{
        return new JSONResult(appRouterManageService.updateMenu(appRouter,file));
    }

    @PostMapping("updateAppRouterNoFile")
    public JSONResult updateAppRouter(@RequestBody AppRouter appRouter){
        return new JSONResult(appRouterManageService.updateMenuNoFile(appRouter));
    }

    @PostMapping("deleteAppRouter")
    public JSONResult deleteAppRouter(@RequestBody AppRouter appRouter){
        return new JSONResult(appRouterManageService.deleteMenu(appRouter));
    }

    @PostMapping("insertAppRouter")
    public JSONResult insertAppRouter(@RequestBody AppRouter appRouter, @RequestParam("file")MultipartFile file) throws Exception{
        return new JSONResult(appRouterManageService.insertMenu(appRouter,file));
    }
}
