package com.cqut.czb.bn.api.controller.vehicleService;

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
 * time: 2019.8.13
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

    @PostMapping("/add")
    public JSONResult add(ServerStandard serverStandard, Principal principal, @RequestParam("file")MultipartFile file) {
        User user = (User)redisUtils.get(principal.getName());
        return service.add(serverStandard, file, user);
    }

    @PostMapping("/delete")
    public JSONResult delete(@Param("serverId") String serverId) {
        return service.delete(serverId);
    }

    @PostMapping("/change")
    public JSONResult change(ServerStandard serverStandard, Principal principal, @RequestParam("file")MultipartFile file) {
        User user = (User)redisUtils.get(principal.getName());
        return service.change(serverStandard, file, user);
    }

    @PostMapping("/changeWithoutImage")
    public JSONResult changeWithoutImage(@RequestBody ServerStandard serverStandard) {
        return service.changeWithoutImage(serverStandard);
    }

    @GetMapping("/search")
    public JSONResult search(ServerStandard serverStandard, PageDTO pageDTO) {
        return service.search(serverStandard, pageDTO);
    }
}
