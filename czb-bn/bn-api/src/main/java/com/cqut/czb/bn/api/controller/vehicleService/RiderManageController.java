package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.vehicleService.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lyk
 * @date: 8/12/2019
 */
@CrossOrigin
@RestController
@RequestMapping("/api/service/rider")
public class RiderManageController {
    @Autowired
    RiderService riderService;

    @GetMapping("/getAllRiders")
    public JSONResult getAllRiders() {
        return new JSONResult(riderService.selectAllRiders());
    }

    @PostMapping("/selectRider")
    public JSONResult selectByPrimaryKey(@RequestParam(name = "riderId") String riderId) {
        return new JSONResult(riderService.selectByPrimaryKey(riderId));
    }
}
