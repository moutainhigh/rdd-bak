package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.TuiKuanDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.VehicleOrderManageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.ServerOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * auth：tsh
 * module：洗车订单管理模块
 * time：2019.8.15
 * version: 1.0
 */
@RequestMapping("/api/ServerOrderController")
@RestController
public class ServerOrderController {
    @Autowired
    ServerOrderService service;

    @PostMapping("/distribute")
    public JSONResult distribute(@RequestBody VehicleOrderManageDTO manageDTO) {
        return service.distribute(manageDTO);
    }

    /**
     * 完成订单
     * @param cleanOrderDTO
     * @return
     */
    @PostMapping("/complete")
    public JSONResult complete(@RequestBody VehicleCleanOrderDTO cleanOrderDTO) {
        return service.complete(cleanOrderDTO);
    }


    @PostMapping("/change")
    public JSONResult change() {
        return service.change();
    }

    /**
     * 洗车订单管理查询
     * @param manageDTO
     * @param pageDTO
     * @return
     */
    @GetMapping("/search")
    public JSONResult search(VehicleOrderManageDTO manageDTO, PageDTO pageDTO) {
        return service.search(manageDTO, pageDTO);
    }

    @PostMapping("/tuiKuan")
    public JSONResult tuiKuan(@RequestBody TuiKuanDTO tuiKuanDTO) {
        return service.tuiKuan(tuiKuanDTO);
    }
}
