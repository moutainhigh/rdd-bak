package com.cqut.czb.bn.api.controller.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.CleanRider;
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

    @GetMapping("/deleteByKey")
    public JSONResult deleteByKey(@RequestParam(name = "riderId") String riderId) {
        return new JSONResult(riderService.deleteByPrimaryKey(riderId));
    }

    @GetMapping("/getAllRiders")
    public JSONResult getAllRiders() {
        return new JSONResult(riderService.selectAllRiders());
    }

    @GetMapping("/getByStatus")
    public JSONResult selectByStatus(@RequestParam(name = "status") Integer status) {
        return new JSONResult(riderService.selectByStatus(status));
    }

    @GetMapping("/getByName")
    public JSONResult selectByName(@RequestParam(name = "riderName") String riderName) {
        return new JSONResult(riderService.selectByName(riderName));
    }

    @PostMapping("/insert")
    public JSONResult insertSelective(CleanRider cleanRider) {
        return new JSONResult(riderService.insertSelective(cleanRider));
    }

    @PostMapping("/updateById")
    public JSONResult updateByPrimaryKeySelective(CleanRider cleanRider) {
        return new JSONResult(riderService.updateByPrimaryKeySelective(cleanRider));
    }

    @GetMapping("/getRiderById")
    public JSONResult selectByPrimaryKey(@RequestParam(name = "riderId") String riderId) {
        return new JSONResult(riderService.selectByPrimaryKey(riderId));
    }
}
