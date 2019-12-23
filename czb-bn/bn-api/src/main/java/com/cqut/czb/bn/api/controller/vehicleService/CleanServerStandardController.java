package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.CleanServerStandardService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * auth: tsh
 * module: 后台管理-洗车服务类型管理模块
 * time: 2019.10.3
 * version: 1
 */
@CrossOrigin
@RestController
@RequestMapping("/api/CleanServerStandard")
public class CleanServerStandardController {
    @Autowired
    CleanServerStandardService service;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 新增服务
     * @param serverStandard
     * @param principal
     * @param file
     * @return
     */
    @PermissionCheck(role = "管理员")
    @PostMapping("/add")
    public JSONResult add(ServerStandard serverStandard, Principal principal, @RequestParam("file")MultipartFile file) {
        User user = (User)redisUtils.get(principal.getName());
        return service.add(serverStandard, file, user);
    }

    /**
     * 删除服务
     * @param serverId
     * @return
     */
    @PermissionCheck(role = "管理员")
    @PostMapping("/delete")
    public JSONResult delete(@Param("serverId") String serverId) {
        return service.delete(serverId);
    }

    /**
     * 编辑服务
     * @param serverStandard
     * @param principal
     * @param file
     * @return
     */
    @PermissionCheck(role = "管理员")
    @PostMapping("/change")
    public JSONResult change(ServerStandard serverStandard, Principal principal, @RequestParam("file")MultipartFile file) {
        User user = (User)redisUtils.get(principal.getName());
        return service.change(serverStandard, file, user);
    }

    /**
     * 无图片编辑
     * @param serverStandard
     * @return
     */
    @PermissionCheck(role = "管理员")
    @PostMapping("/changeWithoutImage")
    public JSONResult changeWithoutImage(@RequestBody ServerStandard serverStandard) {
        return service.changeWithoutImage(serverStandard);
    }

    /**
     * 查询
     * @param serverStandard
     * @param pageDTO
     * @return
     */
    @GetMapping("/search")
    public JSONResult search(ServerStandard serverStandard, PageDTO pageDTO) {
        return service.search(serverStandard, pageDTO);
    }
}
