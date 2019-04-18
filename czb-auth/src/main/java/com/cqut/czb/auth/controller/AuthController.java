package com.cqut.czb.auth.controller;

import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.bn.entity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 该类是注册的api接口，密码使用BCryptPasswordEncoder加密
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserDetailService userDetailService;


    @PostMapping("/register")
    public JSONResult registerUser(@Validated User user){
        return new JSONResult(userDetailService.register(user));
    }

    @PostMapping("/checkAccount")
    public JSONResult checkAccount(@Validated User user){
        return  new JSONResult(userDetailService.checkAccount(user));
    }
}

