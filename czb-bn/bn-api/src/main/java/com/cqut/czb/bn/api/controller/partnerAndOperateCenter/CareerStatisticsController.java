package com.cqut.czb.bn.api.controller.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.global.JSONResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/career")
public class CareerStatisticsController {

    @GetMapping("/statistics")
    public JSONResult statistics(Principal principal) {
        return null;
    }
}
