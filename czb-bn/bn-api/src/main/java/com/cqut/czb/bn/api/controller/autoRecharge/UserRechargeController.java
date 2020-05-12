package com.cqut.czb.bn.api.controller.autoRecharge;


import com.cqut.czb.bn.service.autoRecharge.UserRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/UserRechargeController")
public class UserRechargeController {
    @Autowired
    UserRechargeService userRechargeService;
}
