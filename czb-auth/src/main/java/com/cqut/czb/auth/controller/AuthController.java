package com.cqut.czb.auth.controller;

import com.cqut.czb.bn.entity.entity.VerificationCode;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.auth.service.UserDetailService;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public JSONResult registerUser(@Validated @RequestBody User user){
        return new JSONResult(userDetailService.register(user));
    }

    @PostMapping("/checkAccount")
    public JSONResult checkAccount(@Validated @RequestBody User user){
        return  new JSONResult(userDetailService.checkAccount(user));
    }

    /**
     * 修改密码第一个接口：发送验证码并存入验证码
     * @param phone
     * @return
     */
    @PostMapping("/sendVerificationCode")
    public  JSONResult sendtVerificationCode(@Validated @RequestBody String phone){
        boolean sendVerificationCode=userDetailService.insertVerificationCode(phone);
        if(sendVerificationCode) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "发送成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "发送失败");
        }
    }

    /**
     * 修改密码第一个接口：检查验证码
     * @param verificationCode
     * @return
     */
    @PostMapping("/checkVerificationCode")
    public  JSONResult checkVerificationCode(@Validated @RequestBody VerificationCode verificationCode){
        boolean checkVerificationCode=userDetailService.checkVerificationCode(verificationCode);
        if(checkVerificationCode) {
            return new JSONResult(ResponseCodeConstants.SUCCESS, "修改成功");
        } else {
            return new JSONResult(ResponseCodeConstants.FAILURE, "修改成功");
        }
    }


}

