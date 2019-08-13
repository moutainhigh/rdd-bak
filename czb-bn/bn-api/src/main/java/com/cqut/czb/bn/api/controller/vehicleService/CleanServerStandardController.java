package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerStandard;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.CleanServerStandardService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public JSONResult add(@RequestBody ServerStandard serverStandard) {
        return service.add(serverStandard);
    }

    @PostMapping("/delete")
    public JSONResult delete(@Param("serverId") String serverId) {
        return service.delete(serverId);
    }

    @PostMapping("/change")
    public JSONResult change(@RequestBody ServerStandard serverStandard) {
        return service.change(serverStandard);
    }

    @GetMapping("/search")
    public JSONResult search(ServerStandard serverStandard, PageDTO pageDTO) {
        return service.search(serverStandard, pageDTO);
    }
}
